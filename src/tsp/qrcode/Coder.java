package tsp.qrcode;

import java.util.Arrays;

import static java.lang.Math.abs;
import static tsp.qrcode.qrCode.format;

public class Coder {
    public static void main(String[] args) {
        qrCode code = coder("HELLO WORLD");
        code.afficher();
    }

    public static qrCode coder(String mess){

        // Type des données
        String typeBIN = type("ALPHANUMERIQUE") + "000001011";

        // Encodage des informations
        String infoBIN = encodage(mess);

        // Création de la chaine binaire finale à coder
        String chaine = chaine(typeBIN, infoBIN);

        // Taux de redondance
        char taux = 'Q';

        // Créer la redondance
        String redondance = redondance(infoBIN, taux);

        // Pour chaque masque
        qrCode code = new qrCode();
        int minScore = 0;

        for (int masque = 0; masque < 8; masque++) {
            qrCode codeInter = new qrCode();

            // Calculer le format
            String format = format(taux, masque);

            // Remplissage informations sur le format
            codeInter.update(blockFormat1, format);
            codeInter.update(blockFormat2, format);

            // Remplissage données & redondance avec application du masque
            remplissage(codeInter, chaine + redondance, masque);


            // calcul score
            int score = score(codeInter);

            // On garde le qrCode qui a le score maximal
            if (minScore == 0 || score < minScore) {
                minScore = score;
                code = codeInter;
            }
        }
        return code;
    }

    public static String type(String type) {
        // Code en binaire le type des informations à transmettre
        return switch (type) {
            case "NUMERIQUE" -> "0001";
            case "ALPHANUMERIQUE" -> "0010";
            case "BINAIRE" -> "0100";
            case "KANJI" -> "1000";
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public static String encodage(String message) {
        // Encode un message en une valeur binaire
        StringBuilder result = new StringBuilder();
        char[] charray = message.toCharArray();
        int i;
        for (i = 0; i < charray.length-1; i += 2) {
            int entier = charAt(charray, i) * 45 + charAt(charray, i+1);
            result.append(charToBinary((char) entier, 11));
        }

        if (i+1 == charray.length) {
            result.append(charToBinary((char) charAt(charray, i), 6));
        }

        return result.toString();
    }

    public static String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

    public static int charAt(char[] charray, int i) {
        // Renvoie l'entier ASCII d'un caractère à l'emplacement i
        return alpha.indexOf(charray[i]);
    }

    public static String charToBinary(char letter, int n) {
        // Converti un caractère en nombre binaire codé sur n bits
        return String.format("%" + n + "s", Integer.toBinaryString(letter)).replaceAll(" ", "0");
    }

    public static String chaine(String typeBIN, String infoBIN) {
        // Corrige la chaine binaire
        StringBuilder chaine = new StringBuilder(typeBIN + infoBIN);

        while (chaine.length() % 8 != 0) {
            chaine.append("0");
        }
        String[] octets = {"11101100", "00010001"};
        int choix = 0;
        while (chaine.length() <96) {
            chaine.append(octets[choix]);
            choix = (choix + 1) % 2;
        }
        chaine.append("00000000");
        return chaine.toString();
    }

    public static String redondance(String infoBIN, char taux) {
        // Calcul de la redondance
        //TODO Calcul de la redondance

        // Redondance pour HELLO WORLD :
        return "10101000010010000001011001010010110110010011011010011100000000000010111000001111101101000111101000010000";
    }

    public static String[][] formats = {
            {"111011111000100", "111001011110011", "111110110101010", "111100010011101", "110011000101111", "110001100011000", "110110001000001", "110100101110110"},
            {"101010000010010", "101000100100101", "101111001111100", "101101101001011", "100010111111001", "100000011001110", "100111110010111", "100101010100000"},
            {"011010101011111", "011000001101000", "011111100110001", "011101000000110", "010010010110100", "010000110000011", "010111011011010", "010101111101101"},
            {"001011010001001", "001001110111110", "001110011100111", "001100111010000", "000011101100010", "000001001010101", "000110100001100", "000100000111011"}
    };

    public static String format(char taux, int masque) {
        // Renvoie le format suivant le taux de redondance et le masque choisi
        char[] tauxArray = {'L', 'M', 'Q', 'H'};
        int i = findIndex(tauxArray, taux);
        return formats[i][masque];
    }

    public static int findIndex(char arr[], char t) {
        int index = Arrays.binarySearch(arr, t);
        return (index < 0) ? -1 : index;
    }

    public static int[] blockFormat1 = {168, 169, 170, 171, 172, 173, 175, 176, 155, 113, 92, 71, 50, 29, 8};
    public static int[] blockFormat2 = {428, 407, 386, 365, 344, 323, 302, 181, 182, 183, 184, 185, 186, 187, 188};

    public static int[] block = {
            440, 439, 419, 418, 398, 397, 377, 376,
            356, 355, 335, 334, 314, 313, 293, 292,
            272, 271, 251, 250, 230, 229, 209, 208,
            207, 206, 228, 227, 249, 248, 270, 269,
            291, 290, 312, 311, 333, 332, 354, 353,
            375, 374, 396, 395, 417, 416, 438, 437,
            436, 435, 415, 414, 394, 393, 373, 372,
            352, 351, 331, 330, 310, 309, 289, 288,
            268, 267, 247, 246, 226, 225, 205, 204,
            203, 202, 224, 223, 245, 244, 266, 265,
            287, 286, 308, 307, 329, 328, 350, 349,
            371, 370, 392, 391, 413, 412, 434, 433,
            432, 431, 411, 410, 390, 389, 369, 368,
            348, 347, 327, 326, 306, 305, 285, 284,
            264, 263, 243, 242, 222, 221, 201, 200,
            180, 179, 159, 158, 117, 116, 96 ,  95,
            75 , 74 , 54 , 53 , 33 , 32 , 12 , 11 ,
            10 , 9  , 31 , 30 , 52 , 51 , 73 , 72 ,
            94 , 93 , 115, 114, 157, 156, 178, 177,
            199, 198, 220, 219, 241, 240, 262, 261,
            283, 282, 304, 303, 325, 324, 346, 345,
            367, 366, 388, 387, 409, 408, 430, 429,
            260, 259, 239, 238, 218, 217, 197, 196,
            194, 193, 215, 214, 236, 235, 257, 256,
            255, 254, 234, 233, 213, 212, 192, 191,
            190, 189, 211, 210, 232, 231, 253, 252}; // Indices des informations

    public static void remplissage(qrCode code, String chaine, int masque) {
        // Remplissage du QR Code avec les informations en appliquant le masque
        for (int i = 0; i < block.length; i++) {
            int bit = Integer.parseInt(chaine.substring(i, i+1));
            int module = block[i];
            int[] indice = casesToIndices(module);

            // Valeur du masque
            int masqueBool = boolToInt(masque(indice, masque));

            // Application masque
            bit = (masqueBool + bit) % 2;

            // Mise à jour dans le QR Code
            code.update(module, bit);
        }
    }

    public static boolean masque(int[] indice, int masque) {
        // Renvoie la valeur du masque à l'indice
        int i = indice[0];
        int j = indice[1];
        return switch (masque) {
            case 0 -> (i + j) % 2 == 0;
            case 1 -> (i % 2 == 0);
            case 2 -> (j % 3 == 0);
            case 3 -> ((i + j) % 3 == 0);
            case 4 -> ((i/2 + j/3) % 2 == 0);
            case 5 -> (((i*j) % 2 + (i*j)%3) == 0);
            case 6 -> (((i*j) % 3 + (i*j)) % 2 == 0);
            case 7 -> (((i*j)%3 + i + j) % 2 == 0);
            default -> throw new IllegalStateException("Unexpected value: " + masque);
        };
    }

    public static int boolToInt(boolean bool) {
        // Converti un boolean en entier
        if (bool) {return 1; }
        else {return 0; }
    }

    public static int[] casesToIndices(int n) {
        // Converti une case en indices i, j
        int i = n/format;
        int j = n - format*i;
        return new int[] {i, j};
    }

    public static int score(qrCode code) {
        int score = 0;

        for (int i = 0; i < format; i++) {
            int suite_i = 1;
            int suite_j = 1;
            for (int j = 1; j < format; j++) {
                //Ligne
                if (code.get(i, j-1) == code.get(i, j)) {
                    suite_i ++;
                }
                else {
                    if (suite_i >= 5) {
                        score += (suite_i - 2);
                    }
                    suite_i = 1;

                }

                // Colonne
                if (code.get(j-1, i) + code.get(j,i) != 1) {
                    suite_j ++;
                }
                else {
                    if (suite_j >= 5) {
                        score += (suite_j - 2);
                    }
                    suite_j = 1;
                }

                // Carré
                if (i>0 && code.get(i,j) == code.get(i-1,j) && code.get(i,j) == code.get(i,j-1) && code.get(i,j) == code.get(i-1,j-1)) {
                    score += 3;
                }
            }

            if (suite_i >= 5) {
                score += (suite_i - 2);
            }

            if (suite_j >= 5) {
                score += (suite_j - 2);
            }
        }

        // Motif
        int[] motif = {1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0};
        int l = motif.length;
        for (int i = 0; i < format; i++) {
            for (int j = 0; j < l; j++) {
                boolean bool_i_1 = true;
                boolean bool_i_2 = true;
                boolean bool_j_1 = true;
                boolean bool_j_2 = true;

                for (int k = 0; k < motif.length; k++) {
                    bool_i_1 &= (code.get(i, j+k) == motif[k]);
                    bool_i_2 &= (code.get(i, j+k) == motif[l-k-1]);
                    bool_j_1 &= (code.get(j+k, i) == motif[k]);
                    bool_j_2 &= (code.get(j+k, i) == motif[l-k-1]);
                }
                if (bool_i_1) {
                    score += 40;
                }
                if (bool_i_2) {
                    score += 40;
                }
                if (bool_j_1) {
                    score += 40;
                }
                if (bool_j_2) {
                    score += 40;
                }
            }
        }

        //Total
        double total = code.length();
        double black = code.black();

        score += abs( (int) (((black / total) * 100) - 50)) * 2;

        return score;
    }
}
