package com.sers.backend.service.impl.admin.shop;

import com.alibaba.fastjson.JSONObject;
import com.sers.backend.mapper.ProductMapper;
import com.sers.backend.pojo.Product;
import com.sers.backend.pojo.User;
import com.sers.backend.service.admin.shop.AddProductService;
import com.sers.backend.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class AddProductServiceImpl implements AddProductService {
    @Autowired
    private ProductMapper productMapper;

    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject add(MultipartFile photo, String name, String description, String price) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        JSONObject resp=new JSONObject();
        if(!user.getAdmin()){
            resp.put("message","无管理员权限");
            return resp;
        }
        if(name==null||name.length()==0){
            resp.put("message","名称不能为空");
            return resp;
        }
        if(name.length()>100){
            resp.put("message","名称长度不能大于100");
            return resp;
        }
        if(description==null||description.length()==0){
            resp.put("message","简介不能为空");
            return resp;
        }
        if(description.length()>1000){
            resp.put("message","简介长度不能大于1000");
            return resp;
        }
        if(price==null||price.length()==0){
            resp.put("message","定价不能为空");
            return resp;
        }
        if(!price.matches("-?\\d+(\\.\\d+)?")){
            resp.put("message","定价不正确");
            return resp;
        }
        Integer price_int=Integer.parseInt(price);
        Product product=new Product(null,null,name,description,price_int);
        productMapper.insert(product);
        String path="D:\\temp\\Development project\\SpringBoot\\sers\\web\\src\\assets\\images\\"+product.getId().toString()+".png";
        File toFile=new File(path);
        if (toFile.exists()) {
            toFile.delete();
        }
        try {
            inputStreamToFile(photo.getInputStream(),toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product new_product=new Product(product.getId(),path,name,description,price_int);
        productMapper.updateById(new_product);
        resp.put("message","successful");
        return resp;
    }
}
