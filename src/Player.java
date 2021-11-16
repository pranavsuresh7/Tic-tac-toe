public class Player {
    private String name;
    private int numberOfWins = 0;
    private int numberOfGames = 0;
    private int numberOfTies = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins += numberOfWins;
    }

    public int getNumberOfLoss() {
        return numberOfGames - numberOfWins - numberOfTies;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames += numberOfGames;
    }

    public int getNumberOfTies() {
        return numberOfTies;
    }

    public void setNumberOfTies(int numberOfTies) {
        this.numberOfTies += numberOfTies;
    }
}
