import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args){
        //Criação da lista de produtos
        List<Produto> produtos = Arrays.asList(
                new Produto("Notebook Dell", 4500.0, "Eletrônicos"),
                new Produto("Monitor 24", 850.0, "Eletrônicos"),
                new Produto("Smartphone Galaxy", 2800.0, "Eletrônicos"),
                new Produto("Smart TV 55", 3200.0, "Eletrônicos"),
                new Produto("Fone Bluetooth JBL", 250.0, "Eletrônicos"),
                new Produto("Webcam Full HD", 450.0, "Eletrônicos"),
                new Produto("Clean Code", 120.0, "Livros"),
                new Produto("Java Efetivo", 150.0, "Livros"),
                new Produto("O Codificador Limpo", 90.0, "Livros"),
                new Produto("Padrões de Projeto", 180.0, "Livros"),
                new Produto("Entendendo Algoritmos", 85.0, "Livros"),
                new Produto("Arquitetura Limpa", 140.0, "Livros"),
                new Produto("Cadeira Ergonômica", 1200.0, "Móveis"),
                new Produto("Mesa de Escritório", 750.0, "Móveis"),
                new Produto("Mochila para Notebook", 200.0, "Acessórios"),
                new Produto("Hub USB-C", 110.0, "Acessórios")
        );

        //chamando exercicios do arquivo ProdutosOperacoes
        ProdutoOperacoes.imprimirEletronicos(produtos); //exercicio a
        ProdutoOperacoes.filtroPrecos(produtos); //exercicio b
        ProdutoOperacoes.estoqueLivros(produtos); //exercicio c
        ProdutoOperacoes.listaNomeProdutos(produtos); //exercicio f

        //e. chama metodo exercicio d- buscarProdutoPorNome
        System.out.println("\nExercicio D/E\n");
        ProdutoOperacoes.buscarProdutoPorNome(produtos, "Clean Code")
                .ifPresent(p -> System.out.println("Produto encontrado: " + p));

        ProdutoOperacoes.buscarProdutoPorNome(produtos, "Placa de Video")
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}
