#include <AFMotor.h>

#define STEER_LEFT 0
#define STEER_RIGHT 1
#define STEER_STRAIGHT 2

class MotorSteerWheel {

  private:

    void update();

    int m_motorPin;
    int m_direction;

    AF_DCMotor* m_motor;

  public:

    MotorSteerWheel(int pin, int speed);
    ~MotorSteerWheel();

    void setSpeed(int speed);
    void setDirection(int direction);

};

