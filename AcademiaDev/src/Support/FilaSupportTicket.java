package Support;

import java.util.LinkedList;
import java.util.Queue;

public class FilaSupportTicket {
    private Queue<SupportTicket> filaTickets = new LinkedList<>();

    public void adicionarTicket(SupportTicket ticket) {
        filaTickets.add(ticket);
    }

    public void atenderERemoverTicket() {
        if (!filaTickets.isEmpty()) {
            this.filaTickets.poll(); // Remove o ticket do início da fila
        }else{//se a fila estiver vazia
            System.out.println("Não há tickets para atender.");

        }

    }

    public void listarTickets() {
        if (filaTickets.isEmpty()) {
            System.out.println("Não há tickets na fila.");
        } else {
            System.out.println("Tickets na fila:");
            for (SupportTicket ticket : filaTickets) {
                System.out.println(ticket.toString());
            }
        }
    }

    public boolean estaVazia() {
        return filaTickets.isEmpty();
    }

}
