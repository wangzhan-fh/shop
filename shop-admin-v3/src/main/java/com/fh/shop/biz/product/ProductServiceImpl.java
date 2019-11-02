package com.fh.shop.biz.product;




import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.mapper.product.IProductMapper;
import com.fh.shop.mapper.type.ITypeMapper;
import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.FileUtil;
import com.fh.shop.util.OssUtil;
import com.fh.shop.util.SystemConst;
import com.fh.shop.vo.product.ProductVo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private IProductMapper productMapper;



    /**
     * 查询商品
     * @param productSearchParam
     * @return
     */
    @Override
    public DataTableResult productList(ProductSearchParam productSearchParam) {
        //查询总条数
        Long count = productMapper.findProductByCount(productSearchParam);
        //查询本页数据
        List<Product> list = productMapper.productList(productSearchParam);
        List<ProductVo> productVos = buildProductVo(list);
        DataTableResult dataTableResult = new DataTableResult(productSearchParam.getDraw(), count, count, productVos);
        return dataTableResult;
    }

    private List<ProductVo> buildProductVo(List<Product> products) {
        List<ProductVo> productVos=new ArrayList<>();
        if(products!=null && products.size()>0 ){
            for (Product produc : products) {
                ProductVo productVo1 = new ProductVo();
                productVo1.setId(produc.getId());
                productVo1.setPrice(produc.getPrice()+"");
                productVo1.setProducedDate(DateUtil.data2str(produc.getProducedDate(),DateUtil.Y_M_D));
                productVo1.setProductName(produc.getProductName());
                productVo1.setHotProduct(produc.getHotProduct());
                productVo1.setMainImagePath(produc.getMainImagePath());
                productVo1.setShelves(produc.getShelves());
                productVo1.setStock(produc.getStock());
                productVo1.setBrandName(produc.getBrandName());
                productVo1.setTypeName(produc.getTypeName());
                productVos.add(productVo1);

            }
        }
        return productVos;
    }
    /**
     * 添加商品
     * @param product
     */
    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }


    /**
     * 删除商品
     * @param id
     */
    @Override
    public void deleteProduct(Integer id) {
        productMapper.deleteById(id);

    }

    /**
     * 回显
     * @param id
     * @return
     */
    @Override
    public Product toUpdateProduct(Integer id) {
        Product product = productMapper.selectByid(id);
        return product;
    }

    @Override
    public void updateByShelves(Integer id) {
        Product product = productMapper.selectById(id);
        if(product.getShelves()==1){
            product.setShelves(2);
        }else {
            product.setShelves(1);
        }
        productMapper.updateById(product);

    }

    @Override
    public void updateProduct(Product product) {
        String mainImagePath = product.getMainImagePath();
        if(StringUtils.isEmpty(mainImagePath)){
            product.setMainImagePath(product.getMainImagePath());
        }else{
            OssUtil.deleteFile(product.getOldPath());
        }

        productMapper.updateById(product);
    }

    @Override
    public void updateByhotProduct(Integer id) {
        Product product = productMapper.selectById(id);
        if(product.getHotProduct()==1){
            product.setHotProduct(2);
        }else {
            product.setHotProduct(1);
        }
        productMapper.updateById(product);
    }

    @Override
    public List<Product> productParamList(ProductSearchParam productSearchParam) {
        return productMapper.productParamList(productSearchParam);
    }




    @Override
    public ByteArrayOutputStream buildPdf(List<Product> productList) {
        //创建字节输出流
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            //创建字体样式
            BaseFont bfChinese = BaseFont.createFont("C:\\Windows\\Fonts\\simsun.ttc,1",  BaseFont.IDENTITY_H, 	BaseFont.NOT_EMBEDDED);
            Font fontChinese = new Font(bfChinese, 20, Font.NORMAL);
            //创建文本对象
            Document document =new Document(PageSize.A4);
            //创建书写器
            PdfWriter.getInstance(document, bos);
            //打开文本
            document.open();
            // 通过 com.lowagie.text.Paragraph 来添加文本。可以用文本及其默认的字体、颜色、大小等等设置来创建一个默认段落
            document.add(new Paragraph("用户信息:", fontChinese));
            buildPdfbody(productList, fontChinese, document);


            document.close();

        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bos;
    }

    private void buildPdfbody(List<Product> productList, Font fontChinese, Document document) throws DocumentException {
        // document.newPage();
        // 向文档中添加内容
        for (int i = 0; i < productList.size(); i++) {
            document.add(new Paragraph("商品名："+productList.get(i).getProductName(),fontChinese));
            document.add(new Paragraph("库存："+productList.get(i).getStock(),fontChinese));
            document.add(new Paragraph("生产日期："+DateUtil.data2str(productList.get(i).getProducedDate(),DateUtil.Y_M_D),fontChinese));
            document.add(new Paragraph("价格："+productList.get(i).getPrice(),fontChinese));
            document.add(new Paragraph("\n"));
        }
    }

    @Override
    public File buildWord(List<Product> productList) {
        Map<String,Object> map = new HashMap<>();
        map.put("products", productList);
        //获取word方法
        //创建configuration对象   进行配置
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), SystemConst.TEMPLATE);
        configuration.setDefaultEncoding("UTF-8");
        Template template = null;
        try {
            template = configuration.getTemplate("product.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out=null;
        File file =null;
        try {
            //创建文件对象  临时存放文件位置
            file= new File("D:/"+UUID.randomUUID()+".doc");
            out= new FileOutputStream(file);
            template.process(map,new OutputStreamWriter(out,"utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


}
