import java.util.*;

public class Team {
  private List<Team> victories;

  private String name;
  private int round;
  private int seed;
  private String region;


  public Team(String name) {
    this.name = name;
    this.round = 1;
    this.victories = new ArrayList<Team>(Collections.nCopies(7, (Team) null));
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRound() {
    return this.round;
  }

  public void setRound(int round) {
    this.round = round;
  }

  public int getSeed() {
    return this.seed;
  }

  public void setSeed(int seed) {
    this.seed = seed;
  }

  public List<Team> getVictories() {
    return this.victories;
  }
}
