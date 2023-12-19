import java.io.FileWriter;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class hw3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, String> userData = new HashMap<>();

        System.out.println("Введите порядок, в котором вы будете вводить данные (через пробел) : ");
        System.out.println("1 - Фамилия Имя Отчество");
        System.out.println("2 - Дата рождения");
        System.out.println("3 - Номер телефона");
        System.out.println("4 - Пол");

        String userDataInput = scanner.nextLine();
        String[] userDataArray = userDataInput.split(" ");

        if (userDataArray.length != 4) {
            System.out.println("Вы ввели неверное количество цифр... Нам очень жаль. Попробуйте еще раз.");
        } else if (userDataArray.length != Arrays.stream(userDataArray).distinct().toArray().length) {
            System.out.println("Вы ввели повторяющиеся цифры. Попробуйте еще раз.");
        }

        else {
            for (String selectedNumber : userDataArray) {
                switch (selectedNumber) {

                    case "1":
                        System.out.println("Введите фамилию, имя и отчество через пробел:");
                        String fio = scanner.nextLine().toLowerCase();
                        String[] words = fio.split(" ");

                        if (words.length != 3) {
                            throw new RuntimeException(
                                    "Error. Неправильный формат ввода ФИО. Вероятно, вы ввели больше или меньше информации, чем необходимо.");
                        }

                        String lastName = words[0];
                        String name = words[1];
                        String patronym = words[2];

                        if (!lastName.matches("[а-яА-Я ]+") || !name.matches("[а-яА-Я ]+") ||
                                !patronym.matches("[а-яА-Я ]+")) {
                            throw new RuntimeException(
                                    "Error. Ваши данные введены в другом формате или на другом языке. Мы еще не умеем это обрабатывать.");
                        }

                        String resultName = lastName + " " + name + " " + patronym;
                        userData.put("ФИО", resultName);
                        break;

                    case "2":

                        System.out.println("Введите дату рождения в формате дд.мм.гггг:");
                        String dateOfBirth = scanner.nextLine();
                        String[] dateParts = dateOfBirth.split("\\.");

                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                        sdf.setLenient(false);
                        try {
                            Date birthDate = sdf.parse(dateOfBirth);
                            //переменная не используется в дальнейшем, но если в нее
                            //получается записать,значит дата соответствует формату
                        } catch (ParseException e) {
                            throw new RuntimeException(
                                    "Error. Дата должна быть в формате dd.mm.yyyy");
                        }

                        if (Integer.parseInt(dateParts[2]) < 1900 ||
                                Integer.parseInt(dateParts[2]) > 2023) {
                            throw new RuntimeException(
                                    "Error. Некорректный год введенной даты. Очень вероятно, что человек, родившийся в этом году, не существует.");
                        }

                        userData.put("дата_рождения", dateOfBirth);
                        break;

                    case "3":
                        System.out.println("Введите номер телефона в международном формате (11 цифр):");
                        String phoneNumber = scanner.nextLine();

                        if (phoneNumber.length() != 11) {
                            throw new IllegalArgumentException(
                                    "Некорректная длина номера телефона. Должно быть 11 цифр.");
                        }

                        if (!phoneNumber.matches("[0-9 ]+")) {
                            throw new IllegalArgumentException(
                                    "Некорректный номер телефона, содержащий буквы. В нашей вселенной таких нет. ");
                        }

                        userData.put("номер", phoneNumber);
                        break;

                    case "4":
                        System.out.println("Введите пол (мужской - m, женский - f):");
                        String gender = scanner.nextLine();
                        
                        if (!gender.equals("m") && !gender.equals("f")) {
                            throw new RuntimeException("Некорректный пол. Такого не существует, но мы вас запомним...");
                        }

                        userData.put("пол", gender);
                        break;

                    default:
                        System.out.println(
                                "Мы обнаружили, чтоВы ввели неверную последовательность, содержащую цифру, которой ничего не соответствует. Нам жаль. Попробуйте еще раз.");
                        return;
                }
            }
            try {
                String data = userData.get("ФИО").split(" ")[0];
                String fileName = data + ".txt";
                FileWriter writer = new FileWriter(fileName, true);

                String name = userData.get("ФИО");
                String dateOfBirth = userData.get("дата_рождения");
                double phoneNumber = Double.parseDouble(userData.get("номер"));
                String gender = userData.get("пол");

                String userDataLine = name + " " + dateOfBirth + " " + (long) phoneNumber + " " + gender + "\n";
                writer.write(userDataLine);
                writer.close();
                System.out.println("Данные успешно записаны в файл " + data + ".txt");
            } catch (IOException e) {
                System.out.println("Ошибка при записи файла: " + e.getMessage());
            }
        }
    }
}