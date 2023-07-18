import java.util.ArrayList;
/*
Player Model
@author: Hammad Shaikh
 */
public class Player implements Comparable<Player>{
    private String name;
    public ArrayList<PlayerSeason> seasonList = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //manipulator methods to calculate career averages
    public double getCareerPoints(){
        double avg = 0.0;
        for (int i=0;i<seasonList.size();i++){
            avg += seasonList.get(i).getPoints();
        }
        if (avg > 0.0 && seasonList.size() > 0){
            return avg/seasonList.size();
        }
        else
            return 0.0;
    }
    public double getCareerRebounds(){
        double avg = 0.0;
        for (int i=0;i<seasonList.size();i++){
            avg += seasonList.get(i).getRebounds();
        }
        if (avg > 0.0 && seasonList.size() > 0){
            return avg/seasonList.size();
        }
        else
            return 0.0;
    }
    public double getCareerAssists(){
        double avg = 0.0;
        for (int i=0;i<seasonList.size();i++){
            avg += seasonList.get(i).getAssists();
        }
        if (avg > 0.0 && seasonList.size() > 0){
            return avg/seasonList.size();
        }
        else
            return 0.0;
    }
    //compare method for sorting (Comparable interface)
    @Override
    public int compareTo(Player player) {
        return name.compareTo(player.getName());
    }
}
