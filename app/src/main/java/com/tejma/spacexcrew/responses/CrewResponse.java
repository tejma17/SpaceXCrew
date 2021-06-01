package com.tejma.spacexcrew.responses;

import com.tejma.spacexcrew.pojo.Crew;

import java.util.List;

public class CrewResponse {
    private List<Crew> crew;

    public CrewResponse(List<Crew> crew) {
        this.crew = crew;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
