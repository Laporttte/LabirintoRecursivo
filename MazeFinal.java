/*************************************************************************
    Alunos
        Artur Marcos da Silva - 
        Anna Carla... -
        Flavio G... -

    Labirinto versão: 4.0

    Funções: 
        -> Void main - É a função principal, onde lê o arquivo, procura as posições iniciais/existencia de chaves, chama funções e imprime resultados.
        -> labirintoRecursivo - Função que faz a chamada recursiva, preenche a matriz com simbolos, armazena direções do resultado e possui pontos de return.
        -> fileToArray - Usada p/ preencher a matrizMaze[] com o valor do arquivo txt;
        -> printArray - Usada p/ imprimir a matriz
        -> limpaVet - Usada p/ limpar o vetor principal - (Evita erros ao substituir valores no vetor, tais como retorno de "?")
        -> semcaminho - Verifica se o vetor está preenchido e só retorna caso não estiver, escrevendo um alerta;

 **************************************************************************/

import java.io.*;
public class MazeFinal {
    public static void main (String[] args) throws IOException {
        String path = "LabirintoTXT/mapaBonus1.txt";  File arq = new File(path);  
    
        int qntColuna=0, qntLinha=0, cont=-1, aux=0;
        int EntradaLin=0, EntradaCol=0, startx=0, starty=0; 
        boolean key_door = false;
        char [] vet = new char[100];
        char [] vetKey = new char[100];
        char [] vetDoor = new char[100];
        String result ="";


        String search;
        String [] coordenadas= new String [3]; //serve para armazenar onde estava a chave, e a porta; assim eu passo como novos valores de entrada.

            try {
                FileReader fileReader = new FileReader(arq);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String linha = "";
                    while ( ( linha = bufferedReader.readLine() ) != null) {
                        //System.out.println(linha); // imprime o arquivo, frase por frase.
                            qntColuna = linha.length(); 
                            qntLinha++;
                    }
                    fileReader.close();
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        String matrizMaze [] [] = new String [qntLinha] [qntColuna]; /* Declaração da matriz e preenchimento da matriz */
            fileToArray(matrizMaze, arq);

        for (int i = 0; i < matrizMaze.length; i++) {        /* Procura posição inicial (S) e existencia de chaves.*/    
            for (int j = 0; j < matrizMaze[i].length; j++) {
                if (matrizMaze[i][j].equalsIgnoreCase("S")) {
                    EntradaLin = i; EntradaCol = j;
                    startx = EntradaCol; starty = EntradaLin; // var p/ armazenar coordenada do Start.
                }
                if (matrizMaze[i][j].equalsIgnoreCase("K")) {
                    System.out.println("O labirinto possui chaves.\n");
                    key_door = true;
                }
            }
        }
    if (key_door == true) { 
            search = "K"; aux = 1;
            labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont, key_door, aux, search, coordenadas); // Parte principal
                printArray(matrizMaze); 

            System.out.print("\tInicio -> chave: ");
            semcaminho (vet);
            vetKey = vet;
                for (int j = 0; j < vetKey.length; j++) {
                    System.out.print(vetKey[j]);
                }
                System.out.print("\n\n");
            cont=-1;
            ///antes do limpa
            for (int i = 0; i < vetKey.length; i++) {
                if ((vetKey[i]=='C') || (vetKey[i]=='E') || (vetKey[i]=='B') || (vetKey[i]=='D')) { //Se for valido, armazena
                    result +=vetKey[i];
                }
            }
            ///antes do limpa
            limpaVet (vet);
            if (coordenadas[0]!=null) {
            String key [] = (coordenadas[0].split("-"));
                EntradaLin = Integer.parseInt(key[0]);
                EntradaCol = Integer.parseInt(key[1]);
            }
            fileToArray(matrizMaze, arq); // Prenchimento da matriz


            search = "D"; aux = 2;
            labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont, key_door, aux, search, coordenadas); // Parte principal
                printArray(matrizMaze); 

            System.out.print("\tChave -> porta: ");
            semcaminho (vet);
            vetDoor = vet;
                for (int j = 0; j < vetDoor.length; j++) {
                    System.out.print(vetDoor[j]);
                }
                System.out.print("\n\n");
            cont=-1;

            ///antes do limpa
            for (int i = 0; i < vetDoor.length; i++) {
                if ((vetDoor[i]=='C') || (vetDoor[i]=='E') || (vetDoor[i]=='B') || (vetDoor[i]=='D')) { //Se for valido, armazena
                    result +=vetDoor[i];
                }
            }
            ///antes do limpa


            limpaVet (vet);
            if (coordenadas[0]!=null) {
            String door [] = (coordenadas[1].split("-"));
                EntradaLin = Integer.parseInt(door[0]);
                EntradaCol = Integer.parseInt(door[1]);
            }
                key_door = false;    
            fileToArray(matrizMaze, arq); // Prenchimento da matriz

    } // cabou true
            search = "E";
            //System.out.print("Entrada ="+EntradaLin+"/"+EntradaCol);
            labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont, key_door, aux, search, coordenadas); // Parte principal
                printArray(matrizMaze); 
                                                        //antes de imprimir, passo a posição de start p/ ficar mais visivel
                                                        matrizMaze[starty][startx] = "S";
            System.out.print("\tPorta -> saida: ");
            semcaminho (vet);
            for (int j = 0; j < vet.length; j++) {
                System.out.print(vet[j]);
            }
            System.out.print("\n");


            
            // Imprime valores de saída armazenados no vetor                
            System.out.print("Resultado: ");
            if ((vet[0]!='C') && (vet[0]!='E') && (vet[0]!='B') && (vet[0]!='D')) { //se nao tem nada preenchido
                System.out.print("O labirinto não tem saída\n");
            } else {
                System.out.print(result);
                for (int i = 0; i < vet.length; i++) {
                    System.out.print(vet[i]);
                }
                System.out.print("\n\n");
            }
            
        


    }

    public static int labirintoRecursivo (int EntradaCol,int EntradaLin,
     String matrizMaze [][], int qntColuna, int qntLinha, char vet[], int cont,
     boolean key_door, int aux, String search, String [] coordenadas) {
            cont++;

            /* Se a posição da pessoa ultrapassa a matriz */
            if ( EntradaLin >= qntLinha || EntradaLin < 0 || EntradaCol >= qntColuna || EntradaCol < 0){
                return 0;
            } 

            String posicaoAtual = matrizMaze[EntradaLin][EntradaCol]; // Recebe o valor da posição e não a localização
            
            /* Se tentar ir para uma posição já marcada, retorna */
            if (posicaoAtual.equalsIgnoreCase("x") || posicaoAtual.equals(">") || posicaoAtual.equals("<") || posicaoAtual.equals("v") || posicaoAtual.equals("^")) {
                return 0;
            }   
    








            if ((posicaoAtual.equalsIgnoreCase("k")) && aux==1 && key_door==true) { 
                    coordenadas[0]= EntradaLin+"-"+EntradaCol;
                //System.out.println("posição key: "+EntradaLin+", "+EntradaCol);
                return 1;
            }



            if ((posicaoAtual.equalsIgnoreCase("d")) && aux==2 && key_door==true) { 
                key_door = false;    
                coordenadas[1]= EntradaLin+"-"+EntradaCol;
                //System.out.println("\n\n\nposição door: "+EntradaLin+", "+EntradaCol);
                return 1;
            }



            if (posicaoAtual.equalsIgnoreCase("E") && key_door==false) {
                //System.out.println("\n\n\nposição exit: "+EntradaLin+", "+EntradaCol);
                return 1;
            }



        /* RECURSÃO PARA AVANÇAR NO LABIRINTO */


        matrizMaze[EntradaLin][EntradaCol] = "^";
        if (labirintoRecursivo(EntradaCol, EntradaLin - 1, matrizMaze, qntColuna, qntLinha, vet, cont, key_door, aux, search, coordenadas) !=0){
            vet[cont] += 'C';
             return 1;
        }
        matrizMaze[EntradaLin][EntradaCol] = "v";
        if (labirintoRecursivo(EntradaCol, EntradaLin + 1, matrizMaze, qntColuna, qntLinha, vet, cont, key_door, aux, search, coordenadas) !=0){
            vet[cont] += 'B';
            return 1;
       }

        matrizMaze[EntradaLin][EntradaCol] = "<";
        if (labirintoRecursivo(EntradaCol - 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont, key_door, aux, search, coordenadas) !=0){
            vet[cont] += 'E';
            return 1;
       }

        matrizMaze[EntradaLin][EntradaCol] = ">";
        if (labirintoRecursivo(EntradaCol + 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, cont, key_door, aux, search, coordenadas) !=0){
            vet[cont] += 'D';
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

    public static void limpaVet (char vet[]) {
        char [] limpa = new char [100];
        for (int i = 0; i < limpa.length; i++) {
            vet[i] = limpa[i];
        }
    }

    public static void semcaminho (char vet[]) {
        if ((vet[0]!='C') && (vet[0]!='E') && (vet[0]!='B') && (vet[0]!='D')) { //se nao tem nada preenchido
            System.out.print("Não há caminhos possiveis.\n\n");
        }
}

}