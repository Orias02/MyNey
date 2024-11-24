import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class MyNey {  //Mendeklariskan kelas utama dalam program ini
    
    private static final int MAX_TRANSACTIONS = 100; //Menentukan kapasitas maksimum transaksi yang dapat disimpan dalam program (100 transaksi)
    private static final String[] transactionTypes = new String[MAX_TRANSACTIONS]; //Membuat array untuk menyimpan jenis transaksi
    private static final double[] transactionAmounts = new double[MAX_TRANSACTIONS]; //Membuat array untuk menyimpan jumlah transaksi
    private static final String[] transactionDescriptions = new String[MAX_TRANSACTIONS];//Membuat array untuk menyimpan deskripsi transaksi
    private static int transactionCount = 0; //Menyimpan jumlah transaksi yang telah ditambahkan

    private static double totalIncome = 0; //Variabel pemasukan
    private static double totalExpense = 0; //Variabel pengeluaran
    private static String formatCurrency(double amount) { 
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(amount); //Mengatur format mata uang
        
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true; //Digunakan untuk mengontrol apakah program masih berjalan.

        while (running) { //Menggunakan perulangan while untuk menampilkan menu dan input pengguna
            System.out.println("\n=== Pengelola Keuangan Sederhana ===");
            System.out.println("1. Tambah Pemasukan");
            System.out.println("2. Tambah Pengeluaran");
            System.out.println("3. Lihat Ringkasan Keuangan");
            System.out.println("4. Pengeluaran Tertinggi & Terendah");
            System.out.println("5. Keluar");
            System.out.println("-------------------------------------");
            System.out.print("Pilih opsi (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // menangkap enter

            switch (choice) {
                case 1:
                    addIncome(scanner); //Case 1 untuk memanggil metode pemasukan
                    break;
                case 2:
                    addExpense(scanner); //Case 2 untuk memanggil metode pengeluaran 
                    break;
                case 3:
                    showSummary(); //Case 3 untuk memanggil metode yang menunjukkan ringkasan dari pemasukan dan pengeluaran
                    break;
                case 4:
                    showHighestAndLowestExpenses(); //Case 4 untuk memanggil metode yang menunjukkan pengeluaran tertinggi dan terendah
                    break;
                case 5:
                    running = false; //Untuk program berhenti
                    System.out.println("Terima kasih telah menggunakan aplikasi ini.");
                    break;
                default:
                    System.out.println("Opsi tidak valid. Silakan pilih antara 1-5."); //Jika memasukkan angka selain 1-5
            }
        }

        scanner.close();
    }
    

    private static void addIncome(Scanner scanner) {
        System.out.print("Masukkan jumlah pemasukan: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // menangkap enter

        if (amount <= 0) { //Memvalidasi bahwa nilai pemasukan harus positif
            System.out.println("Jumlah pemasukan harus positif.");
            return;
        }

        System.out.print("Masukkan deskripsi pemasukan: ");
        String description = scanner.nextLine();

        addTransaction("Income", amount, description); //Menyimpan data pemasukan ke array dengan metode addTransaction
        totalIncome += amount; //Menambahkan jumlah pemasukan ke variabel totalIncome
        System.out.println("Pemasukan berhasil ditambahkan." + formatCurrency(amount));
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Masukkan jumlah pengeluaran: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // menangkap enter

        if (amount <= 0) { // Disini juga memvalidasi bahwa nilai pengeluaran harus positif karena nanti bakal dikurangin dengan pemasukan
            System.out.println("Jumlah pengeluaran harus positif.");
            return;
        }

        System.out.print("Masukkan deskripsi pengeluaran: ");
        String description = scanner.nextLine();

        addTransaction("Expense", amount, description); //Menyimpan data pengeluaran ke array dengan metode yang sama seperti pemasukan
        totalExpense += amount; // Menambahkan jumlah pengeluaran ke variabel totalExpense
        System.out.println("Pengeluaran berhasil ditambahkan." + formatCurrency(amount));
    }

    private static void addTransaction(String type, double amount, String description) {
        if (transactionCount >= MAX_TRANSACTIONS) {
            System.out.println("Tidak dapat menambahkan transaksi lagi. Kapasitas penuh."); //Terjadi ketika sudah mencapai batas transaksi
            return;
        }

        transactionTypes[transactionCount] = type; //Menyimpan jumlah tipe Transkasi ke dalam array masing-masing
        transactionAmounts[transactionCount] = amount; //Menyimpan jumlah banyak Transaksi ke dalam array masing-masing
        transactionDescriptions[transactionCount] = description; //Menyimpan jumlah deskripsi Transkasi ke dalam array masing-masing
        transactionCount++; //Menambahkan jumlah transaksi
    }

    private static void showSummary() {
        System.out.println("\n=== Ringkasan Keuangan ===");
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactionTypes[i] + ": " + formatCurrency(transactionAmounts[i]) + " - " + transactionDescriptions[i]); //Menampilkan semua transaksi yang ada di array
        }
        double balance = totalIncome - totalExpense; //Menghitung Pengurangan antara total pemasukan dan total pengeluaran
        System.out.println("\nTotal Pemasukan: " + formatCurrency(totalIncome));
        System.out.println("Total Pengeluaran: " + formatCurrency(totalExpense));
        System.out.println("Saldo Akhir: " + formatCurrency(balance));
    }

    private static void showHighestAndLowestExpenses() {
        if (transactionCount == 0) { //Jika belum ada transaksi yang tersimpan bakal muncul seperti ini
            System.out.println("\nBelum ada transaksi.");
            return;
        }

        double highestExpense = 0; // Variabel Menyimpan pengeluaran tertinggi
        String highestExpenseDescription = "Tidak ada";
        double lowestExpense = Double.MAX_VALUE; // Variabel Menyimpan Pengeluaran terendah
        String lowestExpenseDescription = "Tidak ada";

        boolean foundExpense = false;

        for (int i = 0; i < transactionCount; i++) { //perulangan untuk menelusuri transaksi untuk menemukan pengeluaran tertinggi dan terendah.
            if (transactionTypes[i].equals("Expense")) {
                foundExpense = true;

                if (transactionAmounts[i] > highestExpense) {
                    highestExpense = transactionAmounts[i];
                    highestExpenseDescription = transactionDescriptions[i];
                }

                if (transactionAmounts[i] < lowestExpense) {
                    lowestExpense = transactionAmounts[i];
                    lowestExpenseDescription = transactionDescriptions[i];
                }
            }
        }

        if (!foundExpense) { //Jika tidak ada pengeluaran
            System.out.println("\nBelum ada pengeluaran.");
            return;
        }

        System.out.println("\n=== Pengeluaran Tertinggi & Terendah ===");
        System.out.println("Pengeluaran Tertinggi: " + formatCurrency(highestExpense) + " - " + highestExpenseDescription);
        System.out.println("Pengeluaran Terendah: " + formatCurrency(lowestExpense) + " - " + lowestExpenseDescription);
    }
}
