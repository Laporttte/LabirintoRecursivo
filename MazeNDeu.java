import java.io.*;

public class MazeNDeu {
    public static void main (String[] args) throws IOException {
        String path = "LabirintoTXT/mapa3.txt"; // Escolha do mapa
        File arq = new File(path);  // Variavel que retem o arquivo
            //FileReader leitor = new FileReader(arq); 

    int qntColuna=0, qntLinha=0;

        /* Pega as linhas do arquivo e conta a qnt de linhas e colunas. */
        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
                while ( ( linha = bufferedReader.readLine() ) != null) {
                    System.out.println(linha); // imprime o arquivo, frase por frase.
                        qntColuna = linha.length(); 
                        qntLinha++;
                }
                fileReader.close();
                bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("l "+qntLinha); System.out.println("c "+qntColuna);

        String matrizMaze [] [] = new String [qntLinha] [qntColuna]; // Declaração da matriz que vai o labirinto do arquivo.

        fileToArray(matrizMaze, arq); // Recebe o arquivo e armazena na matriz.
        //printArray(matrizMaze); // Imprime a matriz

        //posicao da entrada(x,y) 
        //posicao da saida (x,y)
        //posicao da chave/door/etc
        int cont=-1;
        int EntradaLin=0, EntradaCol=0; 
        char [] vet = new char[100];
        for (int i = 0; i < matrizMaze.length; i++) {
            for (int j = 0; j < matrizMaze[i].length; j++) {
                if (matrizMaze[i][j].equalsIgnoreCase("E")) {
                    EntradaLin = i; EntradaCol = j;
                }
            }
        }
        labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont);
        System.out.println("\n\n");
        printArray(matrizMaze); // Imprime a matriz
        System.out.println("\nResultado: ");
        for (int i = 0; i < vet.length; i++) {
            System.out.print(vet[i]);
        }


    }

    public static int labirintoRecursivo (int EntradaCol,int EntradaLin, String matrizMaze [][], int qntColuna, int qntLinha, char vet[], int cont) {
        String NPC = matrizMaze[EntradaLin][EntradaCol]; 
        cont++;

         if ( 0 > EntradaCol || EntradaCol >= qntColuna || 0 > EntradaLin || EntradaLin >= qntLinha){
            System.out.println("Saiu do labirinto");
            return 0;
        } 

        
        if (NPC.equalsIgnoreCase("S")) {
            System.out.println("\n\nEntrou a saida, saida fica no "+EntradaLin+", "+EntradaCol);
            System.out.println("Valor = "+matrizMaze[EntradaLin][EntradaCol]);
            System.out.println("Posicao = "+EntradaLin+", "+EntradaCol);
            return 1;
        }

       if (NPC.equalsIgnoreCase("X") || NPC.equals(">") || NPC.equals("<") || NPC.equals("v") || NPC.equals("^")) {
            System.out.println("Bateu com algum obstaculo");
            System.out.println("Valor = "+matrizMaze[EntradaLin][EntradaCol]);
            System.out.println("Posicao = "+EntradaLin+", "+EntradaCol);

            return 0;
        }   

     // Tenta ir para baixo
    if (EntradaLin==qntLinha) {
    if (matrizMaze[EntradaLin+1][EntradaCol].equalsIgnoreCase("X")) {
        System.out.println("Não da pra baixo;");
    } else {
        System.out.println(" --> baixo");
        matrizMaze[EntradaLin][EntradaCol] = "v";
        if (labirintoRecursivo(EntradaCol, EntradaLin + 1, matrizMaze, qntColuna, qntLinha, vet, cont) !=0){
            vet[cont] += 'B';
            return 1; 
        } 
    }}
    
    //Tenta ir para cima.
    if (matrizMaze[EntradaLin-1][EntradaCol].equalsIgnoreCase("X") || EntradaLin<0) {
        System.out.println("Não da pra subir;");
    } else {
        System.out.println(" --> cima");
        vet[cont] += 'C';
        matrizMaze[EntradaLin][EntradaCol] = "^";
        if (labirintoRecursivo(EntradaCol, EntradaLin - 1, matrizMaze, qntColuna, qntLinha, vet, cont) !=0){
            return 1;
        } 
    }

    // Tenta ir para a direita.
    if (matrizMaze[EntradaLin][EntradaCol+1].equalsIgnoreCase("X") || EntradaCol+1>qntColuna) {
        System.out.println("Não da pra ir pra direita;");
    } else {
        System.out.println(" --> direita");
        vet[cont] += 'D';
        matrizMaze[EntradaLin][EntradaCol] = ">";
        if (labirintoRecursivo(EntradaCol + 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont) !=0){
            return 1;
        }
    }

    // Tenta ir para a esquerda.
    if (matrizMaze[EntradaLin][EntradaCol-1].equalsIgnoreCase("X") || EntradaCol-1<0) {
        System.out.println("Não da pra ir pra esquerda;");
    } else {
        System.out.println(" --> esquerda");
        vet[cont] += 'E';
        matrizMaze[EntradaLin][EntradaCol] = "<";
        if (labirintoRecursivo(EntradaCol - 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont) !=0){
            return 1;
        } 
    }

    // Não deu, então volta.
    matrizMaze[EntradaLin][EntradaCol] = " ";
    return 0;
    }
   




    public static void fileToArray (String matrizMaze[][], File arq ) {
        int i=0;
        char caracter;

        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
                while ( ( linha = bufferedReader.readLine() ) != null) {
                    for (int j = 0; j < linha.length(); j++) { //cada vez q rece a linha completa, percorro e registro na 
                        caracter = linha.charAt(j);
                        matrizMaze [i][j] = String.valueOf(caracter);
                    }
                    i++;
                }
                fileReader.close();
                bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printArray (String matrizMaze[][]) {
        for (int i = 0; i < matrizMaze.length; i++) {
            for (int j = 0; j < matrizMaze[i].length; j++) {
                System.out.print(matrizMaze [i][j]);
            }
            System.out.print("\n");
        }
    }
}








/*










 */