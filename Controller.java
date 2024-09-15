import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import templates.View;

public class Controller {
    private View view;
    private List<Model> cows;

    public Controller(View view) {
        this.view = view;
        this.cows = new ArrayList<>();

        initializeCows();
        view.getStartButton().addActionListener(new StartGameListener());
    }

    // เพิ่มวัวแต่ละตัวจากทั้งสามทีม
    private void initializeCows() {
        cows.add(new Model("Cow White 1", "White", false, true));
        cows.add(new Model("Cow Black 1", "Black", true, false));
        cows.add(new Model("Cow Brown 1", "Brown", false, false));

        cows.add(new Model("Cow White 2", "White", false, true));
        cows.add(new Model("Cow Black 2", "Black", true, false));
        cows.add(new Model("Cow Brown 2", "Brown", false, false));

        cows.add(new Model("Cow White 3", "White", false, true));
        cows.add(new Model("Cow Black 3", "Black", true, false));
        cows.add(new Model("Cow Brown 3", "Brown", false, false));
    }

    // ฟังก์ชันสำหรับเริ่มเกมและแสดงผล
    private class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearDisplay();


            // ให้แต่ละวัวโยนโบว์ลิ่ง 10 รอบ
            for (int round = 1; round <= 10; round++) {
                view.displayMessage("Round " + round + ":");
                for (Model cow : cows) {
                    cow.rollBowling();
                    int firstRoll = cow.getFirstRoll(round - 1);
                    int score = cow.getScores()[round - 1];
                    view.displayMessage(cow.getName() + " (" + cow.getTeam() + ") First Roll: " + firstRoll + " scored " + score + " points.");
                }
            }

            // จัดอันดับคะแนนของวัวทั้งหมด
            view.displayMessage("\nFinal Scores:");
            Collections.sort(cows, new Comparator<Model>() {
                @Override
                public int compare(Model c1, Model c2) {
                    return Integer.compare(c2.getTotalScore(), c1.getTotalScore());
                }
            });

            // แสดงอันดับและตรวจสอบว่าคะแนนเท่ากันหรือไม่
            int rank = 1;
            for (int i = 0; i < cows.size(); i++) {
                Model cow = cows.get(i);
                if (i > 0 && cow.getTotalScore() == cows.get(i - 1).getTotalScore()) {
                    view.displayMessage("Rank " + rank + ": " + cow.getName() + " (" + cow.getTeam() + ") - " + cow.getTotalScore() + " points (Tie)");
                } else {
                    view.displayMessage("Rank " + (rank++) + ": " + cow.getName() + " (" + cow.getTeam() + ") - " + cow.getTotalScore() + " points");
                }
            }

            // หาทีมที่ชนะ
            int whiteTeamScore = calculateTeamScore("White");
            int blackTeamScore = calculateTeamScore("Black");
            int brownTeamScore = calculateTeamScore("Brown");

            view.displayMessage("\nTeam Scores:");
            view.displayMessage("Team White: " + whiteTeamScore + " points");
            view.displayMessage("Team Black: " + blackTeamScore + " points");
            view.displayMessage("Team Brown: " + brownTeamScore + " points");

            String winningTeam = findWinningTeam(whiteTeamScore, blackTeamScore, brownTeamScore);
            view.displayMessage("\nThe winning team is: " + winningTeam);

            // หลังจบเกม รีเซ็ตเพื่อให้สามารถเริ่มเกมใหม่ได้
            view.displayMessage("\nPress 'Start Game' to play again.");
        }
    }

    // ฟังก์ชันสำหรับคำนวณคะแนนรวมของแต่ละทีม
    private int calculateTeamScore(String teamName) {
        int teamScore = 0;
        for (Model cow : cows) {
            if (cow.getTeam().equals(teamName)) {
                teamScore += cow.getTotalScore();
            }
        }
        return teamScore;
    }

    // ฟังก์ชันสำหรับหาทีมที่ชนะ
    private String findWinningTeam(int whiteTeamScore, int blackTeamScore, int brownTeamScore) {
        if (whiteTeamScore > blackTeamScore && whiteTeamScore > brownTeamScore) {
            return "White";
        } else if (blackTeamScore > whiteTeamScore && blackTeamScore > brownTeamScore) {
            return "Black";
        } else if (brownTeamScore > whiteTeamScore && brownTeamScore > blackTeamScore) {
            return "Brown";
        } else {
            return "Tie between multiple teams";
        }
    }
}

