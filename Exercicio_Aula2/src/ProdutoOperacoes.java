import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdutoOperacoes {
    //classe com as operacoes para o produto

    //a. imprime produtos da categoria de eletronicos
    public static void imprimirEletronicos(List<Produto> produtos){
        System.out.println("Exercicio A\n");
        System.out.println("Produtos da categoria eletronicos usando forEach");
        produtos.forEach(p -> {
            if("Eletrônicos".equals(p.getCategoria())){
                System.out.println(p.getNome());
            }
        });

        System.out.println("\nProdutos da categoria eletronicos usando stream e filteer");
        produtos.stream()
                .filter(p -> "Eletrônicos".equals(p.getCategoria()))
                .forEach(p -> System.out.println(p.getNome()));
    }

    //b. classe com os produtos com preco maior que 500.0
    public static void filtroPrecos(List<Produto> produtos){
        System.out.println("\nExercicio B\n");
        List<Double> precos = produtos.stream()
                .filter(p -> p.getPreco() > 500.0)
                .map(Produto::getPreco)
                .collect(Collectors.toList());
        System.out.println(precos);
    }

    //c. calcula valor total do estoque de produtos da categoria livros
    public static void estoqueLivros(List<Produto> produtos){
        System.out.println("\nExercicio C\n");
        System.out.println("Valor total de produtos da categoria Livros:");
        double total = produtos.stream()
                .filter(p -> "Livros".equals(p.getCategoria()))
                .mapToDouble(Produto::getPreco)
                .sum();
        System.out.println("Total: R$ " + total);
    }

    //d. metodo patra buscar produtos por nome
    public static Optional<Produto> buscarProdutoPorNome(List<Produto> produtos, String nome){
        return produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    //f. lista de nomes dos produto usando lambda
    public static void listaNomeProdutos(List<Produto> produtos){
        System.out.println("\nExercicio F\n");
        //usando funcao lamba
        System.out.println("Lista de nomes do Produtos: ");
        List<String> nomesL = produtos.stream()
                .map(p -> p.getNome())
                .collect(Collectors.toList());
        System.out.println("Nomes usando lambda: " + nomesL);

        //usando metodo de referencia
        List<String> nomesR = produtos.stream()
                .map(Produto::getNome)
                .collect(Collectors.toList());
        System.out.println("Nomes usando metodo de referencia: " + nomesR);
    }
}
