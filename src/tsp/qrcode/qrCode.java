package tsp.qrcode;

public class qrCode {

    public static int format = 21;

    private int[] code = { // Code de base vierge
            1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1,
            1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    };

    public void update(int[] code){
        this.code = code;
    }

    public void update(int n, int value){
        this.code[n] = value;
    }

    public void update(int i, int j, int value){
        this.code[format*i + j] = value;
    }

    public int length(){
        return this.code.length;
    }

    public int black(){
        int result = 0;
        for (int j : this.code) {
            result += j;
        }
        return result;
    }

    public void update(int[] block, String value) {
        // CONDITION block.length == value.length()
        // Assigne à chaque pixel de block un boolean de value
        char[] chval = value.toCharArray();
        for (int i = 0; i<block.length; i++) {
            this.update(block[i], chval[i] - 48);
        }
    }

    public int get(int n){
        return this.code[n];
    }

    public int get(int i, int j){
        return this.code[format*i + j];
    }

    public void afficher() {
        // Affiche le qr code dans le terminal
        for (int i = 0; i < format; i++) {
            for (int j = 0; j < format; j++) {
                if (this.code[format*i + j] == 0) {
                    System.out.print("⬜️");
                }
                else {
                    System.out.print("⬛️");
                }
            }
            System.out.println();
        }
    }

    public void tab() {
        // Affiche le QR code dans le terminal sous forme d'un tableau java
        System.out.println("{");
        for (int i = 0; i < format; i++) {
            for (int j = 0; j < format; j++) {
                System.out.print(this.code[format*i + j] + ", ");
            }
            System.out.println();
        }
        System.out.print("}");
    }
}
