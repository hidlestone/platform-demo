package com.fallframework.platform.test.starters.elasticsearch;

import com.fallframework.platform.test.starters.elasticsearch.entity.Product;
import com.fallframework.platform.test.starters.elasticsearch.service.ProductService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author zhuangpf
 */
@RestController
@RequestMapping("/es")
public class ElasticsearchController {

	//注入 ElasticsearchRestTemplate
	@Resource(name = "elasticsearchTemplate")
	private ElasticsearchRestTemplate elasticsearchRestTemplate;
	@Autowired
	private ProductService productService;

	@RequestMapping("/createIndex")
	public void createIndex() {
		boolean index = elasticsearchRestTemplate.createIndex(Product.class);
		//创建索引，系统初始化会自动创建索引
		System.out.println("创建索引");
	}

	@RequestMapping("/deleteIndex")
	public void deleteIndex() {
		//创建索引，系统初始化会自动创建索引
		boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);
		System.out.println("删除索引 = " + flg);
	}

	@RequestMapping("/save")
	public void save() {
		Product product = new Product();
		product.setId(2L);
		product.setTitle("苹果手机");
		product.setCategory("手机");
		product.setPrice(2999.0);
		product.setImages("http://www.atguigu/hw.jpg");
		productService.save(product);
	}
	//POSTMAN, GET http://localhost:9200/product/_doc/2

	@RequestMapping("/update")
	public void update() {
		Product product = new Product();
		product.setId(2L);
		product.setTitle("小米 2 手机");
		product.setCategory("手机");
		product.setPrice(9999.0);
		product.setImages("http://www.atguigu/xm.jpg");
		productService.save(product);
	}
	//POSTMAN, GET http://localhost:9200/product/_doc/2

	//根据 id 查询
	@RequestMapping("/findById")
	public void findById() {
		Product product = productService.getById(2L);
		System.out.println(product);
	}

	@RequestMapping("/findall")
	public void findAll() {
		Iterable<Product> products = productService.list();
		for (Product product : products) {
			System.out.println(product);
		}
	}

	//删除
	@RequestMapping("/delete")
	public void delete() {
		Product product = new Product();
		product.setId(2L);
		productService.removeById(product.getId());
	}
	//POSTMAN, GET http://localhost:9200/product/_doc/2

	//批量新增
	@RequestMapping("/saveAll")
	public void saveAll() {
		List<Product> productList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Product product = new Product();
			product.setId(Long.valueOf(i));
			product.setTitle("[" + i + "]小米手机");
			product.setCategory("手机");
			product.setPrice(1999.0 + i);
			product.setImages("http://www.atguigu/xm.jpg");
			productList.add(product);
		}
		productService.saveBatch(productList);
	}

	//分页查询
	/*@RequestMapping("/findByPageable")
	public void findByPageable(){
		//设置排序(排序方式，正序还是倒序，排序的 id)
		Sort sort = Sort.by(Sort.Direction.DESC,"id");
		int currentPage=0;//当前页，第一页从 0 开始， 1 表示第二页
		int pageSize = 5;//每页显示多少条
		//设置查询分页
		PageRequest pageRequest = PageRequest.of(currentPage, pageSize,sort);
		//分页查询
		Page<Product> productPage = productDao.findAll(pageRequest);
		for (Product Product : productPage.getContent()) {
			System.out.println(Product);
		}
	}*/


	/**
	 * 数据查询，返回List
	 *
	 * @param field 查询字段
	 * @param value 查询值
	 * @return List<EmployeeBean>
	 */
	@RequestMapping("/queryMatchList")
	public List<Product> queryMatchList(String field, String value) {
		NativeSearchQueryBuilder builder=new NativeSearchQueryBuilder();
		NativeSearchQuery query=builder.withQuery(QueryBuilders.queryStringQuery(value)).build();
		
		SearchHits<Product> search = elasticsearchRestTemplate.search(query, Product.class);
		Stream<SearchHit<Product>> searchHitStream = search.get();
		return null;
	}

	@RequestMapping("/queryMatchList02")
	public Object queryMatchList02(String field, String value) {
		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery("title", "苹果")).build();
		SearchHits<Product> searchHits =  elasticsearchRestTemplate.search(nativeSearchQuery, Product.class);
		Stream<SearchHit<Product>> searchHitStream = searchHits.get();
		return searchHitStream;
	}

	/**
	 * 数据查询，返回Page
	 *
	 * @param field 查询字段
	 * @param value 查询值
	 * @return AggregatedPage<EmployeeBean>
	 */
	public AggregatedPage<Product> queryMatchPage(String field, String value) {
		return null;
	}
	
}
