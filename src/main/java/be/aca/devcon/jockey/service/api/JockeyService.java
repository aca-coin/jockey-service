package be.aca.devcon.jockey.service.api;

import java.util.List;

public interface JockeyService {

	/**
	 * Finds all jockeys belonging to the specified team in the Guest site.
	 * If teamName is null, all jockeys are returned.
	 */
	List<Jockey> getJockeys(String teamName, int from, int to);

	/**
	 * Finds all jockeys belonging to the specified team in the Guest site.
	 * If teamName is null, all jockeys are returned.
	 */
	List<Jockey> getJockeys(String teamName);

	/**
	 * Find all teams in the Guest site.
	 */
	List<String> getTeams();

	/**
	 * Returns the total amount of jockeys belonging to the specified team in the Guest site.
	 * If teamName is null, the total jockey count is returned.
	 */
	int getJockeysCount(String teamName);
}
