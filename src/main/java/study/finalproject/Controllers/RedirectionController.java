package study.finalproject.Controllers;

import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import study.finalproject.Data.DbConnection;
import study.finalproject.Exceptions.NotFoundException;

@RestController
@RequestMapping("{code}")
public class RedirectionController {
    @GetMapping
    public ModelAndView Redirect(@PathVariable String code) throws SQLException, NotFoundException{
        Connection connection = DbConnection.getInstance();
        var ps = connection.prepareStatement("SELECT url FROM url WHERE code=?");
        ps.setString(1, code);
        var result = ps.executeQuery();
        if (result.next()){
            var url = result.getString("url");
            return new ModelAndView("redirect:" + url);
        }else{
            throw new NotFoundException();
        }
    }

}
