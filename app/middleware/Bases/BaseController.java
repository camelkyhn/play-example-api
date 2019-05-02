package middleware.Bases;

import middleware.Core.GeneralResult;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class BaseController extends Controller {

    protected Result errorResult(int statusCode, GeneralResult result) {
        return Results.status(statusCode, Json.toJson(result));
    }

    protected Result errorResult(int statusCode, Exception exception) {
        GeneralResult result = new GeneralResult();
        result.Error(exception);
        return Results.status(statusCode, Json.toJson(result));
    }
}
