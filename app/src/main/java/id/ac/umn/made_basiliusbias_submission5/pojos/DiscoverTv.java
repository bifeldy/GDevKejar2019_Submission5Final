package id.ac.umn.made_basiliusbias_submission5.pojos;

import java.util.List;

public class DiscoverTv {

    private int page;
    private List<Tv> results;
    private int total_results;
    private int total_pages;

    public DiscoverTv() {}

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Tv> getResults() {
        return results;
    }

    public void setResults(List<Tv> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
