package study.finalproject.Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import study.finalproject.Data.DbConnection;
import study.finalproject.Data.UrlDto;

@RestController
@RequestMapping("api/generate")
public class GenerationController {
    @PostMapping
    public String Generate(@RequestBody UrlDto urlDto) throws SQLException{
        Connection connection = DbConnection.getInstance();
        var ps = connection.prepareStatement("SELECT url, shortUrl FROM url WHERE url=?");
        ps.setString(1, urlDto.url);
        var result = ps.executeQuery();
        if (result.next()){
            var shortUrl = result.getString("shortUrl");
            return shortUrl;
        }else{
            ps = connection.prepareStatement("SELECT code FROM url WHERE code=?");
            ResultSet codeResult;
            String code;
            do{
                code = RandomStringUtils.random(6, "0123456789abcdefghijklmnopqrstuvwxyz");
                ps.setString(1, code);
                codeResult = ps.executeQuery();
            } while (codeResult.next());
            ps = connection.prepareStatement("INSERT INTO url (url, shortUrl, code) VALUES(?, ?, ?)");
            var shortUrl = "http://localhost:8080/" + code;
            ps.setString(1, urlDto.url);
            ps.setString(2, shortUrl);
            ps.setString(3, code);
            ps.executeUpdate();
            return shortUrl;
        }
    } 
}
