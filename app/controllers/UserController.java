package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import middleware.Attributes.Secured;
import middleware.Dtos.Account.LoginDto;
import middleware.Dtos.UserDto;
import middleware.Bases.BaseController;
import middleware.Bases.IController;
import middleware.Core.GeneralResult;
import middleware.Entities.User;
import middleware.Exceptions.IdCanNotBeEmptyException;
import middleware.Exceptions.InvalidModelStateException;
import middleware.Filters.UserFilter;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.User.UserService;

import javax.inject.Inject;
import java.util.List;

public class UserController extends BaseController implements IController {

    private FormFactory _formFactory;
    private UserService _userService;

    @Inject
    public UserController(FormFactory formFactory, UserService userService) {
        _formFactory = formFactory;
        _userService = userService;
    }

    @Override
    @Security.Authenticated(Secured.class)
    public Result detail(Long id) {
        if (id <= 0) {
            return errorResult(BAD_REQUEST, new IdCanNotBeEmptyException());
        }

        GeneralResult<User> result = _userService.detail(id);
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    @Security.Authenticated(Secured.class)
    public Result list() {
        Form<UserFilter> form = _formFactory.form(UserFilter.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<List<User>> result = _userService.list(form.get());
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    @Security.Authenticated(Secured.class)
    public Result create() {
        Form<UserDto> form = _formFactory.form(UserDto.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<Boolean> result = _userService.save(form.get());
        if (result.isFailed()) {
            return errorResult(INTERNAL_SERVER_ERROR, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    @Security.Authenticated(Secured.class)
    public Result edit() {
        Form<UserDto> form = _formFactory.form(UserDto.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<Boolean> result = _userService.update(form.get());
        if (result.isFailed()) {
            return errorResult(INTERNAL_SERVER_ERROR, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    @Security.Authenticated(Secured.class)
    public Result delete(Long id) {
        if (id <= 0) {
            return errorResult(BAD_REQUEST, new IdCanNotBeEmptyException());
        }

        GeneralResult<Boolean> result = _userService.delete(id);
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }

    public Result login() {
        Form<LoginDto> form = _formFactory.form(LoginDto.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<String> tokenResult = _userService.login(form.get());
        if (tokenResult.isFailed()) {
            return errorResult(INTERNAL_SERVER_ERROR, tokenResult);
        }

        GeneralResult<JsonNode> result = new GeneralResult<>();
        JsonNode jsonData = Json.newObject().put("access_token", tokenResult.getData());
        result.Success(jsonData);
        return ok(Json.toJson(result));
    }
}