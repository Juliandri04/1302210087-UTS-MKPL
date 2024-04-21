package lib;

public class TaxFunction {

    private static final int MAX_CHILDREN = 3;
    private static final int TAX_RATE = 5;
    private static final int BASE_TAX_FREE_INCOME = 54000000;
    private static final int ADDITIONAL_TAX_FREE_INCOME_PER_CHILD = 1500000;
    private static final int ADDITIONAL_TAX_FREE_INCOME_FOR_MARRIED = 4500000;

    /**
     * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
     * 
     * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
     * 
     * Penghasilan tidak kena pajak dihitung berdasarkan status pernikahan dan jumlah anak.
     * 
     */
    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        int taxableIncome = calculateTaxableIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible, isMarried, numberOfChildren);
        int tax = (int) Math.round(TAX_RATE / 100.0 * taxableIncome);
        return Math.max(tax, 0); // Tax should not be negative
    }

    private static int calculateTaxableIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        int totalIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
        int taxFreeIncome = calculateTaxFreeIncome(isMarried, numberOfChildren);
        return totalIncome - deductible - taxFreeIncome;
    }

    private static int calculateTaxFreeIncome(boolean isMarried, int numberOfChildren) {
        int taxFreeIncome = BASE_TAX_FREE_INCOME;
        if (isMarried) {
            taxFreeIncome += ADDITIONAL_TAX_FREE_INCOME_FOR_MARRIED;
        }
        taxFreeIncome += Math.min(numberOfChildren, MAX_CHILDREN) * ADDITIONAL_TAX_FREE_INCOME_PER_CHILD;
        return taxFreeIncome;
    }
}
