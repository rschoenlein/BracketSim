public class Main {
  public static void main(String[] args) {
    Bracket bracket = new Bracket();

    System.out.println("Bracket Complete? " + bracket.isComplete());

    System.out.println("Champion: " + bracket.findChampion().getName());

    System.out.println("Champions Path to Victory: ");
    for(Team t: bracket.championsPathToVictory())
      if(t != null)
        System.out.println(t.getName());
      else
        System.out.println("Not Found");
  }
}
