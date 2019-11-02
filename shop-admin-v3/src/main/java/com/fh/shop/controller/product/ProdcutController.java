package com.fh.shop.controller.product;

import com.fh.shop.biz.product.ProductService;
import com.fh.shop.conmmons.DataTableResult;
import com.fh.shop.conmmons.Log;
import com.fh.shop.conmmons.ServerResponse;
import com.fh.shop.param.product.ProductSearchParam;
import com.fh.shop.po.product.Product;
import com.fh.shop.util.ExcelUtil;
import com.fh.shop.util.FileUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static com.fh.shop.util.FileUtil.downloadFile;
import static com.fh.shop.util.FileUtil.excelDownload;

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
        String[] heander={"商品名","价格","生产日期","分类","品牌","库存"};
        String[] prpos={"productName","price","producedDate","typeName","brandName","stock"};
        XSSFWorkbook xwb = ExcelUtil.buildWookBook(productList, "商品列表", heander, prpos, Product.class);
        //下载
        FileUtil.excelDownload(xwb,response);
    }

    @RequestMapping("/downPdf")
    public void downPdf(ProductSearchParam productSearchParam,HttpServletResponse response) throws IOException {
        //获取要导出的数据
        List<Product> productList = productService.productParamList(productSearchParam);
        //将数据转为指定格式
        ByteArrayOutputStream bos = productService.buildPdf(productList);
        //下载
        FileUtil.pdfDownload(response, bos);
    }




    @RequestMapping("/downWord")
    public void downWord(ProductSearchParam productSearchParam,HttpServletResponse response) throws IOException {
        //获取要导出的数据
        List<Product> productList = productService.productParamList(productSearchParam);
        //将数据转为制定格式
        File file =productService. buildWord(productList);
        //下载
        downloadFile(request,response,file.getPath(),file.getName());
        file.delete();
    }


    //上下架
    @RequestMapping("/updateByShelves")
    @ResponseBody
    @Log("商品上下架")
    public ServerResponse updateByShelves(Integer id){
        productService.updateByShelves(id);
        return ServerResponse.success();
    }

    //是否热销
    @RequestMapping("/updateByhotProduct")
    @ResponseBody
    @Log("是否热销")
    public ServerResponse updateByhotProduct(Integer id){
        productService.updateByhotProduct(id);
        return ServerResponse.success();
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
     * @param id
     * @param
     * @return
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

/*    *//**
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
}
