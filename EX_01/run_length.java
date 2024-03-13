public class run_length {
    public static void print_current_code(char code, int count) {
        if (count == 0) {
            System.out.println("Encoding error!");
            System.exit(0);
        }

        switch (code) {
            case 'W':
                if (count == 1)
                    System.out.print("W");
                else
                    System.out.print(count + "W");
                break;
            case 'B':
                if (count == 1)
                    System.out.print("B");
                else
                    System.out.print(count + "B");
                break;
        }
    }

    public static void run_length_encoding(String screen) {
        int count = 1; 
        char currentChar = screen.charAt(0); 

        for (int i = 1; i < screen.length(); i++) {
            if (screen.charAt(i) == currentChar) {
                count++;
            } else {
                print_current_code(currentChar, count);
                currentChar = screen.charAt(i);
                count = 1;
            }
        }

        print_current_code(currentChar, count);
    }

    public static void main(String[] args) {
        String screen = "WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWBWWWWWWWWWWWWWW";
        System.out.println("Run-length encoding(RLE) Test");
        System.out.println("Input data : " + screen);
        System.out.print("Encoding result : ");
        run_length_encoding(screen);
    }
}
