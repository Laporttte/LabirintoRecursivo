/*************************************************************************
    Alunos
        Anna Carla Teixeira da Silva - 00796175
        Artur Marcos da Silva - 00796214
        Flavio Gomes Reis - 796524

    Labirinto versão: 5.4

    Funções: 
        -> Void main - É a função principal, onde lê o arquivo, procura as posições iniciais/existencia de chaves, chama funções e imprime resultados.
        -> labirintoRecursivo - Função que faz a chamada recursiva, preenche a matriz com simbolos, armazena direções do resultado e possui pontos de return.
        -> arqParaMatriz - Usada p/ preencher a matrizMaze[] com o valor do arquivo txt;
        -> imprimeMatriz - Usada p/ imprimir a matriz
        -> limpaVet - Usada p/ limpar o vetor principal - (Evita erros ao substituir valores no vetor, tais como retorno de "?")
        -> semCaminho - Verifica se o vetor está preenchido e só retorna caso não estiver, escrevendo um alerta;

 **************************************************************************/

import java.io.*;
public class Labirinto {
    public static void main (String[] args) throws IOException {
        String path = "LabirintoTXT/mapa5.txt";  File arq = new File(path);  //Escolha do mapa.
    
        int qntColuna=0, qntLinha=0;            // armazena tam do labirinto p/ criar matriz.
        int indexVetor=-1, id=0;                // é aux p/ indice do vetor que armazena respostas | aux é auxiliar p/ identificar o que pesquisar primeiro ("K" ou "D")
        int EntradaLin=0, EntradaCol=0;         // armazena entradas temporariamentes ("S") ou ("S" -> "K" -> "D")
        int startx=0, starty=0;                 // var's p/ armazenar valores do "S" e poder imprimi-lo na posição certa (final do codigo)
        boolean key_door = false;               // boolean p/ existencia de chave
        char [] vet = new char[100];            // Recebe todas as respostas temporariamente
        String result ="";                      // String que armazena todas as respostas
        String [] coordenadas= new String [2];  // serve para armazenar onde estava a chave e a porta; (Assim, as entradas mudam).
        
        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";  // string que recebe as linhas vez por vez
                while ( ( linha = bufferedReader.readLine() ) != null) {    // lê e armazena em string linhas, as linhas do txt
                    qntColuna = linha.length(); // valor da coluna
                    qntLinha++; // valor da linha (apos while terminar)
                }
                fileReader.close();
                bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String matrizMaze [] [] = new String [qntLinha] [qntColuna];    // declaração da matriz
        arqParaMatriz(matrizMaze, arq);   // preenchimento da matriz com os valores do arquivo

        /* Estrutura de repetição p/ verificar existencia de chaves e armazenar posição inicial ("S")/start */
        for (int i = 0; i < matrizMaze.length; i++) {  
            for (int j = 0; j < matrizMaze[i].length; j++) {
                if (matrizMaze[i][j].equalsIgnoreCase("S")) {
                    EntradaLin = i; EntradaCol = j;
                    startx = EntradaCol; starty = EntradaLin;
                }
                if (matrizMaze[i][j].equalsIgnoreCase("K")) {
                    System.out.println("Labirinto possui chave.\n");
                    key_door = true;
                }
            }
        }

        /* Estrutura condicional caso o labirinto possua chaves */
        if (key_door == true) { 
            // Procura a chave      
            id = 1; // o valor 1 informa que a função recursiva deve procurar a chave.

            labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, indexVetor, key_door, id, coordenadas);
                imprimeMatriz(matrizMaze, startx, starty); // imprime a matriz (com simbolos)
            System.out.print("\tInicio -> chave: ");
                semCaminho (vet); // função que só retorna se não tiver um caminho e alerta a não existencia de um.

            for (int j = 0; j < vet.length; j++) {
                System.out.print(vet[j]);
            }
                System.out.print("\n\n");

            indexVetor=-1; // apos receber e imprimir os resultados, reseta aux do index do vetor
            /* enquanto o vetor de chaves tiver posições/valores validos, armazena na string result */
            for (int i = 0; i < vet.length; i++) {
                if ((vet[i]=='C') || (vet[i]=='E') || (vet[i]=='B') || (vet[i]=='D')) { 
                    result +=vet[i];
                }
            }
            limpaVet (vet); // limpa o vetor para evitar erros no armazenamento de novos itens.

            /* string coordenadas recebe posição da chave que será usada como posição inicial p/ encontrar door */
            if (coordenadas[0]!=null) { // verifica se a posição é valida, caso for, é porque o labirinto tem uma saida.
            String key [] = (coordenadas[0].split("-")); // valor de ex: "5-0"
                EntradaLin = Integer.parseInt(key[0]); // linha ex: "5"
                EntradaCol = Integer.parseInt(key[1]); // coluna ex: "0"
            }
                arqParaMatriz(matrizMaze, arq); // Prenchimento da matriz com novas entradas

            // Procura a porta
            id = 2; // o valor 1 informa que a função recursiva deve procurar a chave.

            labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, indexVetor, key_door, id, coordenadas); 
            imprimeMatriz(matrizMaze, startx, starty); // imprime a matriz (com simbolos)

            System.out.print("\tChave -> porta: ");
            semCaminho (vet); // função que só retorna se não tiver um caminho e alerta a não existencia de um.

            for (int j = 0; j < vet.length; j++) {
                System.out.print(vet[j]);
            }
            System.out.print("\n\n");

            indexVetor=-1; // apos receber e imprimir os resultados, reseta aux do index do vetor
            /* enquanto o vetor de portas tiver posições/valores validos, armazena na string result */
            for (int i = 0; i < vet.length; i++) {
                if ((vet[i]=='C') || (vet[i]=='E') || (vet[i]=='B') || (vet[i]=='D')) {
                    result +=vet[i];
                }
            }
            limpaVet (vet); // limpa o vetor para evitar erros no armazenamento de novos itens.

            /* string coordenadas recebe posição da porta que será usada como posição inicial p/ encontrar saida */
            if (coordenadas[0]!=null) { // verifica se a posição é valida, caso for, é porque o labirinto tem uma saida.
            String door [] = (coordenadas[1].split("-")); 
                EntradaLin = Integer.parseInt(door[0]);
                EntradaCol = Integer.parseInt(door[1]);
            }
            key_door = false; // Passa que o valores já foram encontrados.    
                arqParaMatriz(matrizMaze, arq); // Prenchimento da matriz
        } 

            /* Porta até a saida ou Entrada até a saida */
            labirintoRecursivo(EntradaCol, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, indexVetor, key_door, id, coordenadas); 
                imprimeMatriz(matrizMaze, startx, starty); // imprime a matriz (com simbolos)
            
                System.out.print("\tPorta -> saida: ");
                semCaminho (vet); // função que só retorna se não tiver um caminho e alerta a não existencia de um.

            /* enquanto o vetor de portas tiver posições/valores validos, armazena na string result */
            for (int i = 0; i < vet.length; i++) {
                if ((vet[i]=='C') || (vet[i]=='E') || (vet[i]=='B') || (vet[i]=='D')) {
                    result +=vet[i];
                    System.out.print(vet[i]);
                }
            }
            System.out.print("\n");

            /*  Imprime resultados (todos os vetores ou diz que o labirinto não tem saida) */      
            System.out.print("Resultado: ");
            if ((vet[0]!='C') && (vet[0]!='E') && (vet[0]!='B') && (vet[0]!='D')) { //se nao tem nada preenchido
                System.out.print("O labirinto não tem saída\n");
            } else {
                System.out.print(result);
                System.out.print("\n\n");
            }
    }

    public static int labirintoRecursivo (int EntradaCol,int EntradaLin, String matrizMaze [][], int qntColuna, int qntLinha, char vet[], int indexVetor, boolean key_door, int id, String [] coordenadas) {
        indexVetor++;

            /* Se o NPC ultrapassa matriz - retorna um passo */
            if ( EntradaLin >= qntLinha || EntradaLin < 0 || EntradaCol >= qntColuna || EntradaCol < 0){
                return 0;
            } 

            String posicaoAtual = matrizMaze[EntradaLin][EntradaCol]; // recebe o valor da posição
            
            /* Se o NPC tenta um passo já dado - retorna um passo */
            if (posicaoAtual.equalsIgnoreCase("x") || posicaoAtual.equals(">") || posicaoAtual.equals("<") || posicaoAtual.equals("v") || posicaoAtual.equals("^")) {
                return 0;
            }   
            
            // valida se é a hora de procurar a chave, se tem chave no labirinto e se a posição atual é a chave
            if ((posicaoAtual.equalsIgnoreCase("k")) && id==1 && key_door==true) { 
                coordenadas[0]= EntradaLin+"-"+EntradaCol;
                return 1;
            }

            // valida se é a hora de procurar a porta, se tem chave no labirinto e se a posição atual é a porta
            if ((posicaoAtual.equalsIgnoreCase("d")) && id==2 && key_door==true) { 
                coordenadas[1]= EntradaLin+"-"+EntradaCol;
                key_door = false;    
                return 1;
            }

            // valida se é a hora de procurar a saida, se não tem mais chaves para procurar e se a posição atual é a chave
            if (posicaoAtual.equalsIgnoreCase("E") && key_door==false) {
                return 1;
            }

        /* PASSOS RECURSIVOS */
        matrizMaze[EntradaLin][EntradaCol] = "^"; // posição atual recebe valor
        if (labirintoRecursivo(EntradaCol, EntradaLin - 1, matrizMaze, qntColuna, qntLinha, vet, indexVetor, key_door, id, coordenadas) !=0){ // se der diferente de zero (dos return 0), continua a procura do passo recursivo
            vet[indexVetor] += 'C'; // armazena valor no vetor temporario
             return 1; 
        }
        matrizMaze[EntradaLin][EntradaCol] = "v";
        if (labirintoRecursivo(EntradaCol, EntradaLin + 1, matrizMaze, qntColuna, qntLinha, vet, indexVetor, key_door, id, coordenadas) !=0){
            vet[indexVetor] += 'B';
            return 1;
        }

        matrizMaze[EntradaLin][EntradaCol] = "<";
        if (labirintoRecursivo(EntradaCol - 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, indexVetor, key_door, id, coordenadas) !=0){
            vet[indexVetor] += 'E';
            return 1;
        }

        matrizMaze[EntradaLin][EntradaCol] = ">";
        if (labirintoRecursivo(EntradaCol + 1, EntradaLin, matrizMaze, qntColuna, qntLinha, vet, indexVetor, key_door, id, coordenadas) !=0){
            vet[indexVetor] += 'D';
            return 1;
        }

        matrizMaze[EntradaLin][EntradaCol] = " ";  // caso tenha sido retornado, ou seja, o caminho não deu certo. O caminho recebe novamente " ";
            return 0;

    }

    public static void arqParaMatriz (String matrizMaze[][], File arq ) {
        /* lê arquivo e armazena em matriz */
        int i=0; // int para ajudar percorrer linhas.
        char caracter;
        try {
            FileReader fileReader = new FileReader(arq);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
                while ( ( linha = bufferedReader.readLine() ) != null) {
                    for (int j = 0; j < linha.length(); j++) { 
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

    public static void imprimeMatriz (String matrizMaze[][], int startx, int starty) {
        /* imprime matriz */
        matrizMaze[starty][startx] = "S"; // passa valor "S" p/matriz ser impressa corretamente

        for (int i = 0; i < matrizMaze.length; i++) {
            for (int j = 0; j < matrizMaze[i].length; j++) {
                System.out.print(matrizMaze [i][j]);
            }
            System.out.print("\n");
        }
    }

    public static void limpaVet (char vet[]) {
        /* função para que "apague" o que tinha no vet*/
        char [] limpa = new char [100];
        for (int i = 0; i < limpa.length; i++) {
            vet[i] = limpa[i];
        }
    }

    public static void semCaminho (char vet[]) {
        /* função verifica se o vetor está vazio, caso esteja, mostra que não há caminhos */
        if ((vet[0]!='C') && (vet[0]!='E') && (vet[0]!='B') && (vet[0]!='D')) { //se nao tem nada preenchido
            System.out.print("Não há caminhos possiveis.\n\n");
        }
    }   
}