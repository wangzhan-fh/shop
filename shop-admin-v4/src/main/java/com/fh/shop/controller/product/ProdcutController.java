package com.fh.shop.controller.product;

import com.fh.shop.biz.product.ProductService;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import com.fh.shop.util.DateUtil;
import com.fh.shop.util.FileUtil;
import com.fh.shop.util.SystemConst;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fh.shop.util.FileUtil.downloadFile;


@Controller
@RequestMapping("/product")
public class ProdcutController {
    @Resource(name="productService")
    private ProductService productService;

    @Autowired
    private HttpServletRequest request;



    //导出excel()
    @RequestMapping("/downExcel")
    public void downExcel(ProductSearchParam productSearchParam,HttpServletResponse response){
        //获取要导出的数据
        List<Product> productList = productService.productParamList(productSearchParam);
        //获取XSSFWorkbook
        XSSFWorkbook xwb = buildXssfSheets(productList);
        //下载
        FileUtil.excelDownload(xwb,response);
    }

    private XSSFWorkbook buildXssfSheets(List<Product> productList) {
        //将其转为wookbook
        XSSFWorkbook xwb = new XSSFWorkbook();
        //创建页
        XSSFSheet sheet = xwb.createSheet("商品信息");
        //构建标题头
        buildTitleRow(sheet);
        //内容
        for (int i = 0; i < productList.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            Product product = productList.get(i);

            row.createCell(0).setCellValue(product.getProductName());
            row.createCell(1).setCellValue(product.getPrice().toString());
            row.createCell(2).setCellValue(DateUtil.data2str(product.getProducedDate(),DateUtil.Y_M_D));
            row.createCell(3).setCellValue(product.getStock());
        }
        return xwb;
    }

    private void buildTitleRow(XSSFSheet sheet){
        XSSFRow row = sheet.createRow(0);
        String[] titleRow={"商品名","价格","生产日期","库存"};
        for (int i = 0; i < titleRow.length; i++) {
            row.createCell(0).setCellValue(titleRow[i]);
        }

    }


    @RequestMapping("/downPdf")
    public void downPdf(ProductSearchParam productSearchParam,HttpServletResponse response) throws IOException {
        //获取要导出的数据
        List<Product> productList = productService.productParamList(productSearchParam);
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

            // document.newPage();
            // 向文档中添加内容
            for (int i = 0; i < productList.size(); i++) {
                document.add(new Paragraph("商品名："+productList.get(i).getProductName(),fontChinese));
                document.add(new Paragraph("库存："+productList.get(i).getStock(),fontChinese));
                document.add(new Paragraph("生产日期："+DateUtil.data2str(productList.get(i).getProducedDate(),DateUtil.Y_M_D),fontChinese));
                document.add(new Paragraph("价格："+productList.get(i).getPrice(),fontChinese));
                document.add(new Paragraph("\n"));
            }

            document.close();
            FileUtil.pdfDownload(response, bos);
        } catch (DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }


    @RequestMapping("/downWord")
    public void downWord(ProductSearchParam productSearchParam,HttpServletResponse response) throws IOException {
        //获取要导出的数据
        List<Product> productList = productService.productParamList(productSearchParam);
        Map<String,Object> map = new HashMap<>();
        map.put("products", productList);
        //获取word方法
        buildWord(response, map);
    }


    private void buildWord(HttpServletResponse response, Map<String, Object> map) throws IOException {
        //创建configuration对象   进行配置
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), SystemConst.TEMPLATE);
        configuration.setDefaultEncoding("UTF-8");
        Template template = configuration.getTemplate("product.xml");
        FileOutputStream out=null;
        try {
            //创建文件对象  临时存放文件位置
            File file = new File("D:/"+UUID.randomUUID()+".doc");
            out= new FileOutputStream(file);
            template.process(map,new OutputStreamWriter(out,"utf-8"));
            downloadFile(request,response,file.getPath(),file.getName());
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }finally {
            if(null != out){
                out.close();
            }
        }
    }


    /**
     * 跳转查询商品页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "/product/productList";
    }

    /**
     * 查询商品
     * @return
     */
    @RequestMapping("/productList")
    @ResponseBody
    public DataTableResult productList(ProductSearchParam productSearchParam){
        DataTableResult dataTableResult = productService.productList(productSearchParam);
        return dataTableResult;
    }

    /**
     * 添加商品
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @Log("添加商品")
    public ServerResponse add(Product product){
            productService.add(product);
            return ServerResponse.success();
    }


    /**
     * 删除商品
     */
    @RequestMapping("/deleteProduct")
    @ResponseBody
    @Log("删除商品")
    public ServerResponse deleteProduct(Integer id){
            productService.deleteProduct(id);
            return ServerResponse.success();

    }

    /**
     * 回显
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateProduct")
    @ResponseBody
    public ServerResponse toUpdateProduct(Integer id){
            Product product = productService.toUpdateProduct(id);
            return ServerResponse.success(product);
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping("/updateProduct")
    @ResponseBody
    @Log("修改商品")
    public ServerResponse updateProduct(Product product){
            productService.updateProduct(product);
            return ServerResponse.success();
    }


    //修改商品上下架
    @RequestMapping("/updateByShelves")
    @ResponseBody
    @Log("修改商品上下架")
    public ServerResponse updateByShelves(Integer id){
        productService.updateByShelves(id);
        return ServerResponse.success();
    }
}
