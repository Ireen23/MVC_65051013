import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    private String name;
    private String team;
    private int[] scores = new int[10]; // คะแนนของแต่ละรอบ
  
    private int currentRound = 0;
    private List<Integer> totalScores = new ArrayList<>();
    private boolean isCheating; // ทีมวัวดำโกง
    private boolean isHumble; // ทีมวัวขาวถ่อมตัว
    private int currentPins = 10; // จำนวนพินที่เหลือในแต่ละรอบ

    public Model(String name, String team, boolean isCheating, boolean isHumble) {
        this.name = name;
        this.team = team;
        this.isCheating = isCheating;
        this.isHumble = isHumble;
    }

    public void rollBowling() {
        Random random = new Random();
        int firstRoll = random.nextInt(currentPins + 1); // การโยนครั้งแรก

        // ทีมวัวดำโกง (20% โอกาสโกหกว่าโบลว์ลิ่งล้มทั้งหมด)
        if (isCheating && random.nextDouble() < 0.2) {
            firstRoll = currentPins;
        }
        // ทีมวัวขาวถ่อมตัว (10% โอกาสบอกว่าล้างท่อ)
        if (isHumble && random.nextDouble() < 0.1) {
            firstRoll = 0;
        }

        // ถ้าการโยนครั้งแรกล้มทั้งหมด (Strike)
        if (firstRoll == currentPins) {
            scores[currentRound] = 10; // นับเป็น Strike
            totalScores.add(10); // คะแนนทันที
            currentPins = 10; // เริ่มรอบใหม่
            currentRound++;
            return;
        }

        // ถ้าการโยนครั้งแรกไม่ล้มหมด
        int secondRoll = random.nextInt(currentPins - firstRoll + 1); // การโยนครั้งที่สอง
        if (isCheating && random.nextDouble() < 0.2) {
            secondRoll = currentPins - firstRoll; // ทีมวัวดำโกหก
        }
        if (isHumble && random.nextDouble() < 0.1) {
            secondRoll = 0; // ทีมวัวขาวถ่อมตัว
        }

        // นับคะแนนรวมจากการโยนทั้งสองครั้ง
        int totalPins = firstRoll + secondRoll;
        scores[currentRound] = totalPins;
        if (totalPins == currentPins) {
            // ถ้าล้มหมดในสองครั้ง (Spare)
            totalScores.add(10); // นับเป็น Spare
        } else {
            // ถ้าไม่ล้มหมด (Open)
            totalScores.add(totalPins); // นับคะแนนปกติ
        }

        currentPins = 10; // รีเซ็ตจำนวนพิน
        currentRound++;
    }

    public int calculateScore(int round) {
        int score = 0;
        for (int i = 0; i <= round; i++) {
            score += totalScores.get(i);
            // คำนวณโบนัสกรณี Strike หรือ Spare
            if (totalScores.get(i) == 10) {
                if (i < totalScores.size() - 1) {
                    score += totalScores.get(i + 1); // เพิ่มคะแนนรอบต่อไปถ้าเป็น Spare
                }
                if (i < totalScores.size() - 2) {
                    score += totalScores.get(i + 2); // เพิ่มคะแนนสองรอบถ้าเป็น Strike
                }
            }
        }
        return score;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getTotalScore() {
        return calculateScore(currentRound - 1); 
    }

    public boolean isCheating() {
        return isCheating;
    }

    public boolean isHumble() {
        return isHumble;
    }

    public int[] getScores() {
        return scores;
    }

}
