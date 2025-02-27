package racingcar.service;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.constant.Constants;
import racingcar.model.Car;
import racingcar.model.CarList;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private final CarList carList = new CarList(new ArrayList<>());
    private final List<String> winners = new ArrayList<>();
    private String[] carNames;
    private int moveCount;

    public void createGame(String carNames) {
        splitCarNames(carNames);
        for (String carName : this.carNames) {
            validateCarNames(carName);
            carList.addCar(new Car(carName, 0));
        }
    }

    public void splitCarNames(String carNames) {
        this.carNames = carNames.split(",");
    }

    public void validateCarNames(String carName) {
        if (carName.length() > 5) {
            throw new IllegalArgumentException(Constants.INVALID_CAR_NAME_LENGTH);
        }
        if (carName == null || carName.trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.INVALID_CAR_NAME_EMPTY);
        }
    }

    public void playGame() {
        for (Car car : carList.getCars()) {
            int randomNumber = Randoms.pickNumberInRange(0, 9);
            if (randomNumber >= 4) {
                car.move();
            }
        }
    }

    public CarList getCarList() {
        return carList;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(String moveCountStr) {
        int moveCount;
        try {
            moveCount = Integer.parseInt(moveCountStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Constants.INVALID_MOVE_COUNT_NUMBER);
        }

        if (moveCount < 1) {
            throw new IllegalArgumentException(Constants.INVALID_MOVE_COUNT);
        }
        this.moveCount = moveCount;
    }

    public void determineWinner() {
        int maxMoved = getMaxMoved();
        for (Car car : carList.getCars()) {
            if (car.getCarMoved() == maxMoved) {
                winners.add(car.getCarName());
            }
        }
    }

    public int getMaxMoved() {
        int maxMoved = 0;
        for (Car car : carList.getCars()) {
            maxMoved = Math.max(maxMoved, car.getCarMoved());
        }
        return maxMoved;
    }

    public List<String> getWinners() {
        return winners;
    }
}
