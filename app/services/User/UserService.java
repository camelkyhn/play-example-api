package services.User;

import io.ebean.ExpressionList;
import middleware.Bases.IService;
import middleware.Bases.Service;
import middleware.Core.GeneralResult;
import middleware.Dtos.Account.LoginDto;
import middleware.Dtos.Account.RegisterDto;
import middleware.Dtos.UserDto;
import middleware.Entities.User;
import middleware.Enums.Status;
import middleware.Exceptions.LoginFailedException;
import middleware.Exceptions.NotFoundException;
import middleware.Filters.UserFilter;
import org.mindrot.jbcrypt.BCrypt;
import services.JWT.JWTService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class UserService extends Service<Long, User> implements IService<Long, User, UserFilter, UserDto> {

    private JWTService _jwtService;
    private static final String _salt = BCrypt.gensalt();

    @Inject
    public UserService(JWTService jwtService) {
        super(User.class);
        _jwtService = jwtService;
    }

    @Override
    public GeneralResult<User> detail(Long id) {
        GeneralResult<User> result = new GeneralResult<>();
        try {
            User entity = finder.query().where().eq("id", id).findOne();
            if (entity == null) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            result.Success(entity);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<List<User>> list(UserFilter userFilter) {
        GeneralResult<List<User>> result = new GeneralResult<>();
        try {
            ExpressionList<User> resultList = finder.query().where();
            if (userFilter.getName() != null && !userFilter.getName().isEmpty()) {
                resultList = resultList.contains("name", userFilter.getName());
            }

            if (userFilter.getEmail() != null && !userFilter.getEmail().isEmpty()) {
                resultList = resultList.contains("email", userFilter.getEmail());
            }

            result.Success(applyBaseFilter(userFilter, resultList));
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> save(UserDto dto) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            GeneralResult<User> userResult = detail(dto.getUpdatedUserId());
            if (userResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            User entity = new User(dto.getId(), dto.getName(), dto.getEmail(), BCrypt.hashpw(dto.getPassword(), _salt), dto.getStatus(), userResult.getData(), new Date(), new Date());
            finder.db().insert(entity);
            result.Success(true);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> update(UserDto dto) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            User oldUser = finder.byId(dto.getId());
            if (oldUser == null) {
                throw new NotFoundException(UserDto.class.getSimpleName());
            }

            GeneralResult<User> userResult = detail(dto.getUpdatedUserId());
            if (userResult.isFailed()) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            oldUser.setName(dto.getName());
            oldUser.setEmail(dto.getEmail());
            oldUser.setPassword(BCrypt.hashpw(dto.getPassword(), _salt));
            oldUser.setStatus(dto.getStatus());
            oldUser.setUpdatedUser(userResult.getData());
            oldUser.setUpdatedDate(new Date());
            finder.db().update(oldUser);
            result.Success(true);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    @Override
    public GeneralResult<Boolean> delete(Long id) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            finder.deleteById(id);
            result.Success(true);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    public GeneralResult<String> login(LoginDto dto) {
        GeneralResult<String> result = new GeneralResult<>();
        try {
            User user = finder.query().where().eq("email", dto.email).findOne();
            if (user == null) {
                throw new NotFoundException(User.class.getSimpleName());
            }

            if (!user.getPassword().equals(BCrypt.hashpw(dto.password, _salt))) {
                throw new LoginFailedException();
            }

            result = _jwtService.getToken(user.getId(), dto.refreshToken);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }

    public GeneralResult<Boolean> register(RegisterDto dto) {
        GeneralResult<Boolean> result = new GeneralResult<>();
        try {
            // Created by admin
            UserDto newUser = new UserDto(dto.name, dto.email, dto.password, Status.Active, 1L);
            result = save(newUser);
        }
        catch (Exception exception) {
            result.Error(exception);
        }

        return result;
    }
}
