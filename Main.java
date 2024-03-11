import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    static class UserData {
        public String lastName;
        private String firstName;
        private String middleName;
        private LocalDate birthDate;
        private long phoneNumber;
        private char gender;

        public UserData(String lastName, String firstName, String middleName, LocalDate birthDate, long phoneNumber, char gender) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.birthDate = birthDate;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
        }

        public String formatData() {
            return String.format("%s %s %s %s %d %c", lastName, firstName, middleName, birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), phoneNumber, gender);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные (Фамилия Имя Отчество дата_рождения номер_телефона пол):");
        String input = scanner.nextLine().trim();

        try {
            UserData userData = parseUserData(input);
            saveUserDataToFile(userData);
            System.out.println("Данные успешно сохранены в файл.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static UserData parseUserData(String input) throws Exception {
        String[] parts = input.split("\\s+");
        if (parts.length != 6) {
            throw new Exception("Неверное количество данных.");
        }

        String lastName = parts[0];
        String firstName = parts[1];
        String middleName = parts[2];
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            throw new Exception("Неверный формат даты рождения.");
        }

        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(parts[4]);
        } catch (NumberFormatException e) {
            throw new Exception("Неверный формат номера телефона.");
        }

        char gender = parts[5].charAt(0);
        if (gender != 'f' && gender != 'm') {
            throw new Exception("Неверный формат пола (должен быть 'f' или 'm').");
        }

        return new UserData(lastName, firstName, middleName, birthDate, phoneNumber, gender);
    }

    private static void saveUserDataToFile(UserData userData) throws IOException {
        String filename = userData.lastName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(userData.formatData());
            writer.newLine();
        }
    }
}
