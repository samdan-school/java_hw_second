package hw_8;

import java.io.*;

public class SplitFiles {
    SplitFiles(File sourceFile, int numberOfPieces) throws IOException {
        try (
                BufferedInputStream input = new BufferedInputStream(new FileInputStream(sourceFile));
        ) {
            int r, numberOfPiecesCopied = 1, numberOfBytesCopied;
            long copyLength = sourceFile.length() / numberOfPieces;
            String name = "output.bat", targetName;

            while (numberOfPieces >= numberOfPiecesCopied) {
                targetName = name.substring(0, 6) + numberOfPiecesCopied + name.substring(6);
                numberOfBytesCopied = 0;

                try (
                        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(targetName));
                ) {
                    if (numberOfPieces == numberOfPiecesCopied) {
                        while ((r = input.read()) != -1) {
                            output.write((byte) r);
                        }
                    } else {
                        while (copyLength != numberOfBytesCopied) {
                            r = input.read();
                            output.write((byte) r);
                            numberOfBytesCopied++;
                        }
                    }
                }
                numberOfPiecesCopied++;
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Copy sourceFile splitNumber");
            System.exit(1);
        }
        File sourceFile = new File(args[0]);
        if (!sourceFile.exists()) {
            System.out.println("Source file " + args[0] + " does not exist");
            System.exit(2);
        }
        if (args[1].matches(".^0-9")) {
            System.out.println("2nd argument should be number of splits");
            System.exit(3);
        }
        int numberOfPieces = Integer.parseInt(args[1]);

        try {
            new SplitFiles(sourceFile, numberOfPieces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
