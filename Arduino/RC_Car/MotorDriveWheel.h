#include <AFMotor.h>

#define DRIVE_FORWARD 0
#define DRIVE_BACKWARD 1
#define DRIVE_STOP 2

class MotorDriveWheel {

  private:

    void update();

    int m_motorPin;
    int m_direction;

    AF_DCMotor* m_motor;

  public:

    MotorDriveWheel(int pin, int speed);
    ~MotorDriveWheel();

    void setSpeed(int speed);
    void setDirection(int direction);

};

