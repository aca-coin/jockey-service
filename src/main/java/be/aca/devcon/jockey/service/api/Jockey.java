package be.aca.devcon.jockey.service.api;

import java.util.Objects;

public class Jockey {

	private long id;
	private String name;
	private String horse;
	private String teamName;
	private String portraitUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHorse() {
		return horse;
	}

	public void setHorse(String horse) {
		this.horse = horse;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Jockey jockey = (Jockey) o;
		return Objects.equals(id, jockey.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
