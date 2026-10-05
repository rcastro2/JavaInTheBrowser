public class FibonacciConsole {
    public static void main(String[] args) {
        long first = 0, second = 1;
        
        for (int i = 2; i < 50; i++) {
            long next = first + second;
            System.out.println(next);
            first = second;
            second = next;
        }
        System.out.println();
    }
}