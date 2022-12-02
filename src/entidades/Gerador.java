package entidades;

import java.nio.charset.Charset;
import java.util.Random;

public class Gerador {
    Random random = new Random();

    // Fonte:  https://www.delftstack.com/pt/howto/java/random-alphanumeric-string-in-java/
    public String geraString(int i) {
        // bind the length
        byte[] bytearray;
        bytearray = new byte[256];
        String mystring;
        StringBuffer thebuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray);

        mystring
                = new String(bytearray, Charset.forName("UTF-8"));

        thebuffer = new StringBuffer();

        //remove all spacial char
        theAlphaNumericS
                = mystring
                .replaceAll("[^A-Z0-9]", "");

        //random selection
        for (int m = 0; m < theAlphaNumericS.length(); m++) {

            if (Character.isLetter(theAlphaNumericS.charAt(m))
                    && (i > 0)
                    || Character.isDigit(theAlphaNumericS.charAt(m))
                    && (i > 0)) {

                thebuffer.append(theAlphaNumericS.charAt(m));
                i--;
            }
        }

        // the resulting string
        return thebuffer.toString();
    }

    public int randIdade() {
        return random.nextInt(15, 82);
    }

    public int randMatricula() {
        return random.nextInt(1000, 999999);
    }

    public int randBusca(int inicio, int fim) { return random.nextInt(inicio, fim); }
}
