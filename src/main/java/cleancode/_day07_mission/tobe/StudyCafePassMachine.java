package cleancode._day07_mission.tobe;

import cleancode._day07_mission.tobe.exception.AppException;
import cleancode._day07_mission.tobe.io.InputHandler;
import cleancode._day07_mission.tobe.io.OutputHandler;
import cleancode._day07_mission.tobe.model.PassOptions;
import cleancode._day07_mission.tobe.model.PassOrder;
import cleancode._day07_mission.tobe.model.StudyCafeLockerPass;
import cleancode._day07_mission.tobe.model.StudyCafePass;
import cleancode._day07_mission.tobe.model.StudyCafePassType;
import cleancode._day07_mission.tobe.service.PassService;

public class StudyCafePassMachine {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final PassService passService;

    public StudyCafePassMachine() {
        this.inputHandler = new InputHandler();
        this.outputHandler = new OutputHandler();
        this.passService = new PassService();
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType passType = inputHandler.getPassTypeSelectingUserAction();

            PassOptions passOptions = passService.getPassOptions(passType);
            outputHandler.showPassListForSelection(passOptions.getPasses());

            StudyCafePass selectedPass = inputHandler.getSelectPass(passOptions.getPasses());

            StudyCafeLockerPass lockerPass = null;
            if (passType == StudyCafePassType.FIXED) {
                lockerPass = processLockerSelection(selectedPass);
            }

            PassOrder passOrder = PassOrder.of(selectedPass, lockerPass);
            outputHandler.showPassOrderSummary(passOrder);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    private StudyCafeLockerPass processLockerSelection(StudyCafePass selectedPass) {
        StudyCafeLockerPass lockerPass = passService.findCompatibleLockerPass(selectedPass);

        if (lockerPass != null) {
            outputHandler.askLockerPass(lockerPass);
            boolean useLocker = inputHandler.getLockerSelection();

            if (useLocker) {
                return lockerPass;
            }
        }

        return null;
    }
}