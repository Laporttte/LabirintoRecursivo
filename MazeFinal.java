import java.io.*;
public class MazeFinal {
    public static void main (String[] args) throws IOException {
        String path = "LabirintoTXT/mapaBonus2.txt";  File arq = new File(path);  
    
        int qntColuna=0, qntLinha=0, cont=-1, aux=0;
        int EntradaLin=0, EntradaCol=0, startx, starty; 
        boolean key_door = false;
        
        char [] vetKey = new char [100];
        char [] vetDoor = new char [100];
        char [] vet = new char[100];

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

        /* Declaração da matriz */
        String matrizMaze [] [] = new String [qntLinha] [qntColuna]; 
            fileToArray(matrizMaze, arq); // Prenchimento da matriz

        /* Encontra a entrada da matriz - Posições iniciais p/ começar / verifica existencia de chave + porta*/    
        for (int i = 0; i < matrizMaze.length; i++) {
            for (int j = 0; j < matrizMaze[i].length; j++) {
                if (matrizMaze[i][j].equalsIgnoreCase("S")) {
                    EntradaLin = i; EntradaCol = j;
                }
                if (matrizMaze[i][j].equalsIgnoreCase("K")) {
                    System.out.println("Tem chave no labirinto");
                    key_door = true;
                }
            }
        }
 
        startx = EntradaCol; starty = EntradaLin;
        labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, vetDoor, vetKey, cont, key_door, aux); // Parte principal
            
        //antes de imprimir, passo a posição de start p/ ficar mais visivel
            matrizMaze[starty][startx] = "S";
            System.out.println("\n\n"); printArray(matrizMaze); // Imprime a matriz
            
            /* Imprime valores de saída armazenados no vetor */                
            System.out.println("\nResultado: ");
            if ((vet[0]!='C') && (vet[0]!='E') && (vet[0]!='B') && (vet[0]!='D')) { //se nao tem nada preenchido
                System.out.print("Não há caminhos possiveis.");
            } else {
                for (int i = 0; i < vet.length; i++) {
                    if(i==0) {
                        System.out.print("\nStart até chave: ");
                            for (int j = 0; j < vetKey.length; j++) {
                                System.out.print(vetKey[j]);
                            }
                        System.out.print("\nResto: ");

                    }
                    System.out.print(vet[i]);
                }
            }
            



    }

    public static int labirintoRecursivo (int EntradaCol,int EntradaLin, String matrizMaze [][], int qntColuna, int qntLinha, char vet[],char vetDoor[],char vetKey[], int cont, boolean key_door, int aux) {
            cont++;



            /* Se a posição da pessoa ultrapassa a matriz */
            if ( EntradaLin >= qntLinha || EntradaLin < 0 || EntradaCol >= qntColuna || EntradaCol < 0){
                System.out.println("Saiu do labirinto");
                return 0;
            } 

            String posicaoAtual = matrizMaze[EntradaLin][EntradaCol]; // Recebe o valor da posição e não a localização



            aux = searchKey(posicaoAtual);

            if (key_door) { //tem chave, logo tenho que procurar primeiro a chave -> porta -> saida;
                if (aux > 0) {
                     System.out.println("Achei a chave e somei aux");
                }
                    if (searchDoor(posicaoAtual) > 0 && aux > 0) {
                        System.out.println("Achei a porta");
                        key_door = false;
                    }

            } else { //procuro a saida;
                                /* Se a posição é S => Encontrou a saída */
                                if (posicaoAtual.equalsIgnoreCase("E")) {
                                    System.out.println("Saida! posição: "+EntradaLin+", "+EntradaCol);
                                    //System.out.println("\tValor = "+matrizMaze[EntradaLin][EntradaCol]);
                                    //System.out.println("\tPosicao = "+EntradaLin+", "+EntradaCol);
                                    return 1;
                                }
            }



            /* Se tentar ir para uma posição já marcada, retorna */
            if (posicaoAtual.equalsIgnoreCase("x") || posicaoAtual.equals(">") || posicaoAtual.equals("<") || posicaoAtual.equals("v") || posicaoAtual.equals("^")) {
                //System.out.println("Obstaculo");
                //System.out.println("Valor = "+matrizMaze[EntradaLin][EntradaCol]);
                //System.out.println("Posicao = "+EntradaLin+", "+EntradaCol);
                return 0;
            }   
    
        /* RECURSÃO PARA AVANÇAR NO LABIRINTO */


        matrizMaze[EntradaLin][EntradaCol] = "^";
        if (labirintoRecursivo(EntradaCol, EntradaLin - 1, matrizMaze, qntColuna, qntLinha, vet,vetDoor, vetKey, cont, key_door, aux) !=0){
            vet[cont] += 'C';

            if (aux <= 0) { //se ainda nao encontrou a chave, armazena os valores no vetkey
                vetKey[cont] = 'C';
            }

             return 1;
        }
        matrizMaze[EntradaLin][EntradaCol] = "v";
        if (labirintoRecursivo(EntradaCol, EntradaLin + 1, matrizMaze, qntColuna, qntLinha, vet,vetDoor, vetKey, cont, key_door, aux) !=0){
            vet[cont] += 'B';
            if (aux <= 0) { //se ainda nao encontrou a chave, armazena os valores no vetkey
                vetKey[cont] += 'B';
            }
            return 1;
       }

        matrizMaze[EntradaLin][EntradaCol] = "<";
        if (labirintoRecursivo(EntradaCol - 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet,vetDoor, vetKey, cont, key_door, aux) !=0){
            vet[cont] += 'E';
            
            if  (aux <= 0) { //se ainda nao encontrou a chave, armazena os valores no vetkey
                vetKey[cont] = 'E';
            }
            return 1;
       }

        matrizMaze[EntradaLin][EntradaCol] = ">";
        if (labirintoRecursivo(EntradaCol + 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet,vetDoor, vetKey, cont, key_door, aux) !=0){
            vet[cont] += 'D';
            
            if (aux <= 0) { //se ainda nao encontrou a chave, armazena os valores no vetkey
                vetKey[cont] = 'D';
            }
            return 1;
       }

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

    public static int searchKey (String posicaoAtual) {
        if (posicaoAtual.equalsIgnoreCase("K")) {
            return 1;
        }
        return 0;
    }
    
    public static int searchDoor (String posicaoAtual) { 
        if (posicaoAtual.equalsIgnoreCase("D")) {
            return 1;
        }
        return 0;
    }


}