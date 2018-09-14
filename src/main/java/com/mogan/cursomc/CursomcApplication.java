package com.mogan.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mogan.cursomc.domain.Categoria;
import com.mogan.cursomc.domain.Cidade;
import com.mogan.cursomc.domain.Cliente;
import com.mogan.cursomc.domain.Endereco;
import com.mogan.cursomc.domain.Estado;
import com.mogan.cursomc.domain.ItemPedido;
import com.mogan.cursomc.domain.Pagamento;
import com.mogan.cursomc.domain.PagamentoComBoleto;
import com.mogan.cursomc.domain.PagamentoComCartao;
import com.mogan.cursomc.domain.Pedido;
import com.mogan.cursomc.domain.Produto;
import com.mogan.cursomc.domain.enums.EstadoPagamento;
import com.mogan.cursomc.domain.enums.TipoCliente;
import com.mogan.cursomc.repositories.CategoriaRepository;
import com.mogan.cursomc.repositories.CidadeRepository;
import com.mogan.cursomc.repositories.ClienteRepository;
import com.mogan.cursomc.repositories.EnderecoRepository;
import com.mogan.cursomc.repositories.EstadoRepository;
import com.mogan.cursomc.repositories.ItemPedidoRepository;
import com.mogan.cursomc.repositories.PagamentoRepository;
import com.mogan.cursomc.repositories.PedidoRepository;
import com.mogan.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtosRepository;
	@Autowired
	EstadoRepository estadosRepository;
	@Autowired
	CidadeRepository cidadesRepository;
	@Autowired
	ClienteRepository clientesRepository;
	@Autowired
	EnderecoRepository enderecosRepository;
	@Autowired
	PedidoRepository pedidosRepository;
	@Autowired
	PagamentoRepository pagamentosRepository;
	@Autowired
	ItemPedidoRepository itensPedidosRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.setProdutos(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.setCategorias(Arrays.asList(cat1));
		p2.setCategorias(Arrays.asList(cat1, cat2));
		p3.setCategorias(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtosRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2, c3));
		
		estadosRepository.saveAll(Arrays.asList(est1, est2));
		cidadesRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria", "maria@gmail.com", "12345678912", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("123456", "098765"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clientesRepository.saveAll(Arrays.asList(cli1));
		enderecosRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 21:20"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 10:30"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidosRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentosRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.0);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itensPedidosRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
}
