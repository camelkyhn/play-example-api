package middleware.Bases;

import play.mvc.Result;

public interface IController {

    Result detail(Long id);

    Result list();

    Result create();

    Result edit();

    Result delete(Long id);
}
