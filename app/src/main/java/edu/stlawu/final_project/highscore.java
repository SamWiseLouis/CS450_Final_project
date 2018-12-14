package edu.stlawu.final_project;


// basic class to store highscores


public class highscore {
    String username;
    String score;

    public highscore(){

    }

    public highscore(String user, String aScore){
        this.username = user;
        this.score = aScore;
    }

    public String getUsername() {
        return username;
    }
    public String getScore(){
        return score;
    }
}
