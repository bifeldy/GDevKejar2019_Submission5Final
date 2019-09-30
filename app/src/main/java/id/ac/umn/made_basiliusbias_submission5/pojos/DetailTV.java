package id.ac.umn.made_basiliusbias_submission5.pojos;

import java.util.List;

public class DetailTV extends Tv {

    private List<CreatedBy> created_by;
    private int[] episode_run_time;
    private String first_air_date;
    private List<Genres> genres;
    private String homepage;
    private boolean in_production;
    private String[] languages;
    private String last_air_date;
    private EpisodeToAir last_episode_to_air;
    private EpisodeToAir next_episode_to_air;
    private List<Networks> networks;
    private int number_of_episodes;
    private int number_of_seasons;
    private List<ProductionCompanies> production_companies;
    private List<Seasons> seasons;
    private String status;
    private String type;

    public DetailTV() {}

    public List<CreatedBy> getCreated_by() {
        return created_by;
    }

    public void setCreated_by(List<CreatedBy> created_by) {
        this.created_by = created_by;
    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(int[] episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    @Override
    public String getFirst_air_date() {
        return first_air_date;
    }

    @Override
    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public EpisodeToAir getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(EpisodeToAir last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public EpisodeToAir getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(EpisodeToAir next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public List<Networks> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Networks> networks) {
        this.networks = networks;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public List<ProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompanies> production_companies) {
        this.production_companies = production_companies;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}