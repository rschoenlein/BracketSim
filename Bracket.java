import java.util.*;

public class Bracket {

  private HashMap<String, HashMap<Integer, Team>> regions;
  private Team champion;
  private HashMap<Team, Team> matchups;

  //add (seed, team) to region
  private boolean seedTeam(Team team, int seed, String region) {

    for(String key: this.regions.keySet()) {
      for(Team t: this.regions.get(key).values()) {
        if(t.getName().equals(team.getName())) {
          System.out.println("ERROR: Team already seeded");
          return false;
        }
      }
    }

    team.setSeed(seed);
    team.setRegion(region);
    this.regions.get(region).put(team.getSeed(), team);

    return true;
  }

  // fill regions with teams, fill matchups with teams, query user to advance teams until we find a champion
  public Bracket() {
    this.champion = null;

    this.regions = new HashMap<>();

    regions.put("SOUTH", new HashMap<>());
    regions.put("WEST", new HashMap<>());
    regions.put("EAST", new HashMap<>());
    regions.put("MIDWEST", new HashMap<>());

    //seed teams from the command line and add them to bracket
    int seed = 1;
    int region = 0;

    Scanner in = new Scanner(System.in);

    while(region < 4) {

      System.out.println("What Team is Seed " + seed + " in the " + (String)this.regions.keySet().toArray()[region] + " region?");
      String name = in.nextLine();

      boolean seeded = this.seedTeam(new Team(name), seed, (String)this.regions.keySet().toArray()[region]);

      if(seeded) {
        if(seed == 16) {
          seed = 0;
          region++;
        }

        seed++;
      }
    }

    //fill out initial matchups
    this.matchups = new LinkedHashMap<>();
    for(String key: this.regions.keySet()) {
      this.matchups.put(this.regions.get(key).get(16), this.regions.get(key).get(1));
      this.matchups.put(this.regions.get(key).get(1), this.regions.get(key).get(16));
      this.matchups.put(this.regions.get(key).get(9), this.regions.get(key).get(8));
      this.matchups.put(this.regions.get(key).get(8), this.regions.get(key).get(9));


      this.matchups.put(this.regions.get(key).get(12), this.regions.get(key).get(5));
      this.matchups.put(this.regions.get(key).get(5), this.regions.get(key).get(12));
      this.matchups.put(this.regions.get(key).get(13), this.regions.get(key).get(4));
      this.matchups.put(this.regions.get(key).get(4), this.regions.get(key).get(13));

      this.matchups.put(this.regions.get(key).get(11), this.regions.get(key).get(6));
      this.matchups.put(this.regions.get(key).get(6), this.regions.get(key).get(11));
      this.matchups.put(this.regions.get(key).get(14), this.regions.get(key).get(3));
      this.matchups.put(this.regions.get(key).get(3), this.regions.get(key).get(14));

      this.matchups.put(this.regions.get(key).get(10), this.regions.get(key).get(7));
      this.matchups.put(this.regions.get(key).get(7), this.regions.get(key).get(10));
      this.matchups.put(this.regions.get(key).get(15), this.regions.get(key).get(2));
      this.matchups.put(this.regions.get(key).get(2), this.regions.get(key).get(15));
    }

    //find a champion
    while(this.findChampion() == null) {
      System.out.println("Choose a team to advance: ");
      String name = in.next();
      this.advanceTeam(new Team(name));
    }
  }

  //advance team to next round, eliminate opponent from bracket, create new matchups if needed
  public void advanceTeam(Team team) {

    for(String key: this.regions.keySet()) {
      for(Team t: this.regions.get(key).values()) {
        if(t.getName().equals(team.getName())) {

          Team opponent = matchups.get(t);
          if(opponent != null) {

            this.regions.get(key).remove(opponent.getSeed());
            this.matchups.remove(opponent);

            t.getVictories().add(opponent);
          }

          t.setRound(t.getRound() + 1);

          //update matchups,
          this.matchups.replace(t, null);
          this.updateMatchups();

          return;
        }
      }
    }

    System.out.println("ERROR: Could not find team");
  }

  //return true if all matchups are complete
  public boolean isComplete() {

    if(matchups.isEmpty())
      return true;

    return false;
  }

  //find champion(team in round 7)
  public Team findChampion() {
    if(this.champion != null)
      return this.champion;

    for(String key: this.regions.keySet()) {
      for(Team t: this.regions.get(key).values()) {
        if(t.getRound() == 7) {
          this.champion = t;
          return this.champion;
        }
      }
    }

    return null;
  }

  public List<Team> championsPathToVictory() {
    return this.champion.getVictories();
  }

  //creates new matchups after a team has advanced if needed
  public void updateMatchups() {

    List<Team> keyList = new ArrayList<Team>(this.matchups.keySet());

    for(int i = 0; i < keyList.size() - 1; i+=2) {
      Team value1 = this.matchups.get(keyList.get(i));
      Team value2 = this.matchups.get(keyList.get(i + 1));
      if(value1 == null && value2 == null) {
        this.matchups.replace(keyList.get(i), keyList.get(i + 1));
        this.matchups.replace(keyList.get(i + 1), keyList.get(i));
      }
    }

    System.out.println();
    System.out.println("Current Matchups: ");

    for(Team key: this.matchups.keySet()) {
      String opponent;
      String seed;
      String region;

      if(this.matchups.get(key) == null) {
        opponent = "TBD";
        seed = "";
        region = "";
      } else {
        opponent = this.matchups.get(key).getName();
        seed = Integer.toString(this.matchups.get(key).getSeed());
        region =  this.matchups.get(key).getRegion();
      }
      System.out.println(key.getRegion() + " " + Integer.toString(key.getSeed()) + " " + key.getName() + " VS " + region + " " + seed + " " + opponent);
    }
  }
}
