/*
PlayerSeason Model
@author: Hammad Shaikh
 */
public class PlayerSeason implements Comparable<PlayerSeason>{

    private String year;
    private double points;
    private double rebounds;
    private double assists;
    private String name;

    public PlayerSeason(String year, double points, double rebounds, double assists, String name) {
        this.year = year;
        this.points = points;
        this.rebounds = rebounds;
        this.assists = assists;
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public double getRebounds() {
        return rebounds;
    }

    public void setRebounds(double rebounds) {
        this.rebounds = rebounds;
    }

    public double getAssists() {
        return assists;
    }

    public void setAssists(double assists) {
        this.assists = assists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //compare method for sorting (Comparable interface)
    @Override
    public int compareTo(PlayerSeason season) {
        int comparison1 = Double.compare(Integer.parseInt(this.year), Integer.parseInt(season.getYear()));
        if (comparison1 != 0){
            return comparison1;
        }
        return Double.compare(season.getPoints(), this.points);
    }
}
