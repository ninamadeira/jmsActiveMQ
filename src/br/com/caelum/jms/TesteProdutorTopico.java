package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TesteProdutorTopico {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");
        MessageProducer producer = session.createProducer(topico);

        //Message message = session.createTextMessage("<pedido><id>333</id></pedido>");
        //message.setBooleanProperty("ebook", true);
        
        Pedido pedido = new PedidoFactory().geraPedidoComValores();
		Message message = session.createObjectMessage(pedido);
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }
}
