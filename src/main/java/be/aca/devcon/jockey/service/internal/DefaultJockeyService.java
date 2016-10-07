package be.aca.devcon.jockey.service.internal;

import be.aca.devcon.jockey.service.api.Jockey;
import be.aca.devcon.jockey.service.api.JockeyService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component(immediate = true, service = JockeyService.class)
public class DefaultJockeyService implements JockeyService {

	private static final Log LOGGER = LogFactoryUtil.getLog(DefaultJockeyService.class);

	@Reference private TeamLocalService teamLocalService;
	@Reference private Portal portal;
	@Reference private UserLocalService userLocalService;
	@Reference private GroupLocalService groupLocalService;

	private Long guestGroupId;

	public List<Jockey> getJockeys(String teamName, int from, int to) {
		List<User> users;
		try {
			if (teamName == null) {
				users = userLocalService.getGroupUsers(getGuestGroupId(), from, to);
			} else {
				users = userLocalService.getTeamUsers(getTeamId(getGuestGroupId(), teamName), from, to);
			}
		} catch (PortalException e) {
			LOGGER.error(e);
			return Collections.emptyList();
		}
		return users.stream().map(this::toJockey).collect(Collectors.toList());
	}

	public List<Jockey> getJockeys(String teamName) {
		return getJockeys(teamName, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public int getJockeysCount(String teamName) {
		try {
			if (teamName == null) {
				return userLocalService.getGroupUsersCount(getGuestGroupId());
			} else {
				return userLocalService.getTeamUsersCount(getTeamId(getGuestGroupId(), teamName));
			}
		} catch (PortalException e) {
			LOGGER.error(e);
			return 0;
		}
	}

	public List<String> getTeams() {
		try {
			return teamLocalService.getGroupTeams(getGuestGroupId()).stream().map(Team::getName).collect(Collectors.toList());
		} catch (PortalException e) {
			LOGGER.error(e);
			return Collections.emptyList();
		}
	}

	private long getGuestGroupId() throws PortalException {
		if (guestGroupId == null) {
			long companyId = portal.getDefaultCompanyId();
			guestGroupId = groupLocalService.getGroup(companyId, GroupConstants.GUEST).getGroupId();
		}
		return guestGroupId;
	}

	private Jockey toJockey(User user) {
		Jockey jockey = new Jockey();

		jockey.setId(user.getUserId());
		jockey.setName(user.getFullName());
		jockey.setHorse((String) user.getExpandoBridge().getAttribute("Horse", false));
		jockey.setTeamName(user.getTeams().isEmpty() ? null : user.getTeams().get(0).getName());

		return jockey;
	}

	private long getTeamId(long groupId, String teamName) throws PortalException {
		return teamLocalService.getTeam(groupId, teamName).getTeamId();
	}
}
