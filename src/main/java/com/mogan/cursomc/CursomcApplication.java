package com.mogan.cursomc;

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
import com.mogan.cursomc.domain.Produto;
import com.mogan.cursomc.domain.enums.TipoCliente;
import com.mogan.cursomc.repositories.CategoriaRepository;
import com.mogan.cursomc.repositories.CidadeRepository;
import com.mogan.cursomc.repositories.ClienteRepository;
import com.mogan.cursomc.repositories.EnderecoRepository;
import com.mogan.cursomc.repositories.EstadoRepository;
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
		
	}
}
