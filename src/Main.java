import data.AccountJSONHandler;
import model.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountJSONHandler jsonHandler = new AccountJSONHandler();
        List<BankAccount> accounts = jsonHandler.loadBankAccounts();

        boolean running = true;
        while (running) {
            System.out.println("|----------------------------|");
            System.out.println("| Добро пожаловать в Е-Банк. |");
            System.out.println("|----------------------------|");
            System.out.println("| 1. Создать новый аккаунт   |");
            System.out.println("| 2. Войти по ID в аккаунт   |");
            System.out.println("| 3. Выход                   |");
            System.out.println("|----------------------------|");

            System.out.print("Выберите вариант: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createNewAccount(scanner, jsonHandler, accounts);
                    break;
                case 2:
                    loginAccount(scanner, accounts);
                    break;
                case 3:
                    System.out.println("|------------------------|");
                    System.out.println("| Рады, что были с нами! |");
                    System.out.println("|------------------------|");
                    jsonHandler.saveData(accounts);
                    running = false;
                    break;
                default:
                    System.out.println("|-----------------------------------|");
                    System.out.println("| Неверный выбор, попробуйте снова. |");
                    System.out.println("|-----------------------------------|");
            }
            System.out.println();
        }
        scanner.close();
    }
    private static int generateNewId(List<BankAccount> accounts) {
        return accounts.stream().mapToInt(BankAccount::getID).max().orElse(0) + 1;
    }

    private static void createNewAccount(Scanner scanner, AccountJSONHandler accountJSONHandler, List<BankAccount> accounts) {

        int id = generateNewId(accounts);
        System.out.println("|------------------------------------------------------------|");
        System.out.println("| Регистрация                                                |");
        System.out.println("|------------------------------------------------------------|");

        String idString = "Присвоен новый ID: " + id;
        String chertochki = " ".repeat(60 - idString.length() - 1);

        System.out.println("| Присвоен новый ID: " + id + chertochki + "|");
        System.out.println("|------------------------------------------------------------|");
        System.out.print("Введите начальный баланс: ");
        double balance = scanner.nextDouble();

        System.out.println("|------------------------------------------------------------|");
        System.out.println("| Введите годовую процентную ставку (например, 0.05 для 5%): |");
        System.out.println("|------------------------------------------------------------|\n");

        double annualInterestRate = getDoubleFromUser(scanner);

        BankAccount newAccount = new BankAccount(id, balance, annualInterestRate);
        accounts.add(newAccount);
        System.out.println("|------------------------------------------------------------|");
        System.out.println("| Аккаунт создан успешно!                                    |");
        System.out.println("|------------------------------------------------------------|");
        accountJSONHandler.saveData(accounts);

    }
    private static void loginAccount(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Введите ID аккаунта: ");
        int id = scanner.nextInt();

        Optional<BankAccount> existingAccount = accounts.stream()
                .filter(acc -> acc.getID() == id).findFirst();

        if (existingAccount.isPresent()) {
            BankAccount account = existingAccount.get();
            System.out.println("|------------------------------------------------------------|");

            String WelcomeID = "| Добро пожаловать, ID: " + account.getID();
            String chertochki = " ".repeat(60 - WelcomeID.length() + 1);
            System.out.println("| Добро пожаловать, ID: " + account.getID() + chertochki + "|");

            System.out.println("|------------------------------------------------------------|");

            String balance = "| Текущий баланс: " + account.getBalance();
            chertochki = " ".repeat(60 - balance.length() + 1);
            System.out.println("| Текущий баланс: " + account.getBalance() + chertochki + "|");

            String InterestRate = "| Годовая процентная ставка: " + account.getAnnualInterestRate();
            chertochki = " ".repeat(60 - InterestRate.length() + 1);
            System.out.println("| Годовая процентная ставка: " + account.getAnnualInterestRate() + chertochki + "|");
            System.out.println("|------------------------------------------------------------|");

        } else {
            System.out.println("Аккаунт с таким ID не найден.");
        }
    }
    private static double getDoubleFromUser(Scanner scanner)
    {
        String input = scanner.nextLine();
        double value = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Введите значение (используйте запятую для дробной части): ");
            input = scanner.nextLine().replace(",", ".");

            try
            {
                value = Double.parseDouble(input); // Парсим введённое значение
                validInput = true;  // Если парсинг успешен, выходим из цикла
            } catch (NumberFormatException e)
            {
                System.out.println("Ошибка: введите корректное число.");
            }
        }
        return value;
    }

}