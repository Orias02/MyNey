import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Transaction {
    String type;  // "Income" atau "Expense"
    double amount;
    String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return type + ": " + amount + " - " + description;
    }
}

public class Keuangan_Tugas_Akhir {
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static double totalIncome = 0;
    private static double totalExpense = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Pengelola Keuangan Sederhana ===");
            System.out.println("1. Tambah Pemasukan");
            System.out.println("2. Tambah Pengeluaran");
            System.out.println("3. Lihat Ringkasan Keuangan");
            System.out.println("4. Keluar");
            System.out.print("Pilih opsi (1-4): ");
            
            try {
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        addIncome(scanner);
                        break;
                    case 2:
                        addExpense(scanner);
                        break;
                    case 3:
                        showSummary();
                        break;
                    case 4:
                        running = false;
                        System.out.println("Terima kasih telah menggunakan aplikasi ini.");
                        break;
                    default:
                        System.out.println("Opsi tidak valid. Silakan pilih antara 1-4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        scanner.close();
    }

    // Menambahkan pemasukan
    private static void addIncome(Scanner scanner) {
        try {
            System.out.print("Masukkan jumlah pemasukan: ");
            double amount = scanner.nextDouble();
            if (amount <= 0) {
                System.out.println("Jumlah pemasukan harus positif.");
                return;
            }
            scanner.nextLine(); // menangkap enter
            System.out.print("Masukkan deskripsi pemasukan: ");
            String description = scanner.nextLine();

            transactions.add(new Transaction("Income", amount, description));
            totalIncome += amount;
            System.out.println("Pemasukan berhasil ditambahkan.");
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Pastikan jumlah yang dimasukkan adalah angka.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    // Menambahkan pengeluaran
    private static void addExpense(Scanner scanner) {
        try {
            System.out.print("Masukkan jumlah pengeluaran: ");
            double amount = scanner.nextDouble();
            if (amount <= 0) {
                System.out.println("Jumlah pengeluaran harus positif.");
                return;
            }
            scanner.nextLine(); // menangkap enter
            System.out.print("Masukkan deskripsi pengeluaran: ");
            String description = scanner.nextLine();

            transactions.add(new Transaction("Expense", amount, description));
            totalExpense += amount;
            System.out.println("Pengeluaran berhasil ditambahkan.");
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Pastikan jumlah yang dimasukkan adalah angka.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    // Menampilkan ringkasan keuangan
    private static void showSummary() {
        System.out.println("\n=== Ringkasan Keuangan ===");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        double balance = totalIncome - totalExpense;
        System.out.println("\nTotal Pemasukan: " + totalIncome);
        System.out.println("Total Pengeluaran: " + totalExpense);
        System.out.println("Saldo Akhir: " + balance);
    }
}
