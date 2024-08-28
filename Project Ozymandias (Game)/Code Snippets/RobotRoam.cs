using Pathfinding;
using System.Collections;
using System.Collections.Generic;
using System.Runtime.CompilerServices;
using UnityEngine;

public class RobotRoam : MonoBehaviour
{
    public int job = 0;
    private int speed = 1;

    private bool SERVERonWayBack = false;
    private bool SERVERswitchOWB = false;
    private bool SERVERresetOWB = false;

    private bool STORAGEonWayBack = false;
    private bool STORAGEswitchOWB = false;
    private bool STORAGEresetOWB = false;

    private bool CHARGEonWayBack = false;
    private bool CHARGEswitchOWB = false;
    private bool CHARGEresetOWB = false;

    private GameObject robot;
    public GameObject[] floor0;
    public GameObject[] floor1;
    public GameObject[] floor2;
    public GameObject target;
    public int floor;
    private Animator animator;
    private Animator previousAnimator;
    private Animator thisAnimator;

    private RobotChangeFloor rcf;
    private AIDestinationSetter destinationSetter;

    private Vector2 previousFrame;
    private Vector2 currentFrame;

    public GameObject[] chargers;
    public bool[] chargersOccupied;
    private ClickRobot cr;
    public float batteryPercentage;

    void Start()
    {
        cr = this.gameObject.GetComponent<ClickRobot>();
        robot = this.gameObject;
        destinationSetter = robot.GetComponent<AIDestinationSetter>();
        animator = target.GetComponent<Animator>();
        rcf = this.gameObject.GetComponent<RobotChangeFloor>();
        thisAnimator = this.gameObject.GetComponent<Animator>();
        //InvokeRepeating("", 0, 1);
    }

    // Update is called once per frame
    void Update()
    {
        batteryPercentage = cr.getBatteryPercentage();
        cr.setOperation(job);
        CheckEmptyChargers();
        previousFrame = currentFrame;
        currentFrame = gameObject.transform.position;

        if (previousFrame.x > currentFrame.x)
        {
            gameObject.transform.localScale = new Vector3(-0.3f, 0.3f);
        }
        else
        {
            gameObject.transform.localScale = new Vector3(0.3f, 0.3f);
        }

        switch (job)
        {
            case 0:
                STORAGEswitchOWB = true;
                SERVERswitchOWB = true;
                roam();
                break;
            case 1:
                STORAGEswitchOWB = true;
                serverRoom();
                break;
            case 2:
                storageRoom();
                SERVERswitchOWB = true;
                break;
            case 3:
                SERVERswitchOWB = true;
                STORAGEswitchOWB = true;
                charge();
                break;
            case 4:
                SERVERswitchOWB = true;
                STORAGEswitchOWB = true;
                thisAnimator.SetBool("Idle", true);
                cr.startCharge();
                break;
        }

        if(batteryPercentage <= 0.16 && job != 4)
        {
            job = 3;
        }
        else if (batteryPercentage >= 0.99 && job != 4)
        {
            job = 0;
            if(floor == 0) 
            {
                target = GameObject.Find("ComputerFloor0");
                destinationSetter.target = GameObject.Find("ComputerFloor0").GetComponent<Transform>();
            }
            else if(floor == 1) 
            {
                target = GameObject.Find("Floor1Left");
                destinationSetter.target = GameObject.Find("Floor1Left").GetComponent<Transform>();
            }
            else if (floor == 2)
            {
                target = GameObject.Find("Floor2Right");
                destinationSetter.target = GameObject.Find("Floor2Right").GetComponent<Transform>();
            }
        }
    }

    private void serverRoom()
    {
        //Debug.Log(SERVERonWayBack);
        if (SERVERswitchOWB)
        {
            SERVERonWayBack = true;
            SERVERswitchOWB = false;
        }

        if (target.gameObject.name == "RobotElevatorFloor0")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator0").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor1")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator1").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor2")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator2").GetComponent<Animator>();
        }

        if (SERVERonWayBack)
        {
            switch (rcf.getFloor())
            {
                case 1:
                    target = GameObject.Find("RobotElevatorFloor1");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor1").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 2:
                    target = GameObject.Find("RobotElevatorFloor2");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor2").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(0, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("ComputerFloor0");
                destinationSetter.target = GameObject.Find("ComputerFloor0").GetComponent<Transform>();
            }
            else if (target.name == "ComputerFloor0" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                SERVERonWayBack = false;
                SERVERresetOWB = true;
            }
        }
        if (!SERVERonWayBack)
        {
            if (SERVERresetOWB)
            {
                target = GameObject.Find("RobotElevatorFloor0");
                destinationSetter.target = target.transform;
                SERVERresetOWB = false;
            }
            if (target.name == "RobotElevatorFloor0" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(1, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("Floor1ServerRoom");
                destinationSetter.target = target.transform;
            }
            else if (target.name == "Floor1ServerRoom" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                SERVERonWayBack = true;
            }
        }
    }

    private void storageRoom()
    {
        if (STORAGEswitchOWB)
        {
            STORAGEonWayBack = true;
            STORAGEswitchOWB = false;
        }

        if (target.gameObject.name == "RobotElevatorFloor0")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator0").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor1")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator1").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor2")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator2").GetComponent<Animator>();
        }

        if (STORAGEonWayBack)
        {
            switch (rcf.getFloor())
            {
                case 1:
                    target = GameObject.Find("RobotElevatorFloor1");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor1").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 2:
                    target = GameObject.Find("RobotElevatorFloor2");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor2").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(0, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("ComputerFloor0");
                destinationSetter.target = GameObject.Find("ComputerFloor0").GetComponent<Transform>();
            }
            else if (target.name == "ComputerFloor0" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                STORAGEonWayBack = false;
                STORAGEresetOWB = true;
            }
        }
        if (!STORAGEonWayBack)
        {
            if (STORAGEresetOWB)
            {
                target = GameObject.Find("RobotElevatorFloor0");
                destinationSetter.target = target.transform;
                STORAGEresetOWB = false;
            }
            if (target.name == "RobotElevatorFloor0" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(2, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("Floor2StorageRoom");
                destinationSetter.target = target.transform;
            }
            else if (target.name == "Floor2StorageRoom" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                previousAnimator.SetBool("Open", false);
                STORAGEonWayBack = true;
            }
        }
    }

    private void charge()
    {
        if (CHARGEswitchOWB)
        {
            CHARGEonWayBack = true;
            CHARGEswitchOWB = false;
        }

        if (target.gameObject.name == "RobotElevatorFloor0")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator0").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor1")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator1").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor2")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator2").GetComponent<Animator>();
        }


        if (chargersOccupied[0] == false)
        {
            switch (rcf.getFloor())
            {

                case 1:
                    target = GameObject.Find("RobotElevatorFloor1");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor1").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 2:
                    target = GameObject.Find("RobotElevatorFloor2");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor2").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(0, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("RobotChargerCheckpoint1");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint1").GetComponent<Transform>();
            }
            else if (target.name == "ComputerFloor0")
            {
                target = GameObject.Find("RobotChargerCheckpoint1");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint1").GetComponent<Transform>();
            }
            else if (target.name == "RobotChargerCheckpoint1" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                if (!GameObject.Find("Charger1").GetComponent<Charger>().GetOccupied())
                {
                    target = GameObject.Find("RobotChargePoint1");
                    destinationSetter.target = GameObject.Find("RobotChargePoint1").GetComponent<Transform>();
                    chargers[0].GetComponent<Charger>().SetOccupied(true);
                    StartCoroutine(releaseCharger(chargers[0]));
                    job = 4;
                    floor = 0;
                }
            }
        }
        else if (chargersOccupied[0] == true && chargersOccupied[1] == false)
        {
            switch (rcf.getFloor())
            {
                case 1:
                    target = GameObject.Find("RobotElevatorFloor1");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor1").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 2:
                    target = GameObject.Find("RobotElevatorFloor2");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor2").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(0, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("RobotChargerCheckpoint2");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint2").GetComponent<Transform>();
            }
            else if (target.name == "ComputerFloor0")
            {
                target = GameObject.Find("RobotChargerCheckpoint2");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint2").GetComponent<Transform>();
            }
            else if (target.name == "RobotChargerCheckpoint2" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                if (!GameObject.Find("Charger2").GetComponent<Charger>().GetOccupied())
                {
                    target = GameObject.Find("RobotChargePoint2");
                    destinationSetter.target = GameObject.Find("RobotChargePoint2").GetComponent<Transform>();
                    chargers[1].GetComponent<Charger>().SetOccupied(true);
                    StartCoroutine(releaseCharger(chargers[1]));
                    job = 4;
                    floor = 0;
                }
            }
        }
        else if (chargersOccupied[0] == true && chargersOccupied[1] == true && chargersOccupied[2] == false)
        {
            switch (rcf.getFloor())
            {
                case 0:
                    target = GameObject.Find("RobotElevatorFloor0");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor0").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 2:
                    target = GameObject.Find("RobotElevatorFloor2");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor2").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(1, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("RobotChargerCheckpoint3");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint3").GetComponent<Transform>();
            }
            else if (target.name == "Floor1Right" || target.name == "Floor1Left" || target.name == "Floor1ServerRoom")
            {
                target = GameObject.Find("RobotChargerCheckpoint3");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint3").GetComponent<Transform>();
            }
            else if (target.name == "RobotChargerCheckpoint3" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                target = GameObject.Find("RobotChargePoint3");
                destinationSetter.target = GameObject.Find("RobotChargePoint3").GetComponent<Transform>();
                chargers[2].GetComponent<Charger>().SetOccupied(true);
                StartCoroutine(releaseCharger(chargers[2]));
                job = 4;
                floor = 1;

            }

        }
        else if (chargersOccupied[0] == true && chargersOccupied[1] == true && chargersOccupied[2] == true && chargersOccupied[3] == false)
        {
            switch (rcf.getFloor())
            {
                case 0:
                    target = GameObject.Find("RobotElevatorFloor0");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor0").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 2:
                    target = GameObject.Find("RobotElevatorFloor2");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor2").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(1, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("RobotChargerCheckpoint4");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint4").GetComponent<Transform>();
            }
            else if (target.name == "Floor1Right" || target.name == "Floor1Left" || target.name == "Floor1ServerRoom")
            {
                target = GameObject.Find("RobotChargerCheckpoint4");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint4").GetComponent<Transform>();
            }
            else if (target.name == "RobotChargerCheckpoint4" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {

                target = GameObject.Find("RobotChargePoint4");
                destinationSetter.target = GameObject.Find("RobotChargePoint4").GetComponent<Transform>();
                chargers[3].GetComponent<Charger>().SetOccupied(true);
                StartCoroutine(releaseCharger(chargers[3]));
                job = 4;
                floor = 1;
            }
        }
        else if (chargersOccupied[0] == true && chargersOccupied[1] == true && chargersOccupied[2] == true && chargersOccupied[3] == true && chargersOccupied[4] == false)
        {
            switch (rcf.getFloor())
            {
                case 0:
                    target = GameObject.Find("RobotElevatorFloor0");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor0").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 1:
                    target = GameObject.Find("RobotElevatorFloor1");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor1").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(2, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("RobotChargerCheckpoint5");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint5").GetComponent<Transform>();
            }
            else if (target.name == "Floor2Right" || target.name == "Floor2Left" || target.name == "Floor1StorageRoom")
            {
                target = GameObject.Find("RobotChargerCheckpoint5");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint5").GetComponent<Transform>();
            }
            else if (target.name == "RobotChargerCheckpoint5" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                target = GameObject.Find("RobotChargePoint5");
                destinationSetter.target = GameObject.Find("RobotChargePoint5").GetComponent<Transform>();
                chargers[4].GetComponent<Charger>().SetOccupied(true);
                StartCoroutine(releaseCharger(chargers[4]));

                job = 4;
                floor = 2;

            }
        }
        else if (chargersOccupied[0] == true && chargersOccupied[1] == true && chargersOccupied[2] == true && chargersOccupied[3] == true && chargersOccupied[4] == true && chargersOccupied[5] == false)
        {
            switch (rcf.getFloor())
            {
                case 0:
                    target = GameObject.Find("RobotElevatorFloor0");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor0").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
                case 1:
                    target = GameObject.Find("RobotElevatorFloor1");
                    destinationSetter.target = GameObject.Find("RobotElevatorFloor1").GetComponent<Transform>();
                    animator.SetBool("Open", true);
                    break;
            }

            if ((target.name == "RobotElevatorFloor0" || target.name == "RobotElevatorFloor1" || target.name == "RobotElevatorFloor2") && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                rcf.changeToFloor(2, robot);
                animator.SetBool("Open", false);
                previousAnimator.SetBool("Open", false);
                target = GameObject.Find("RobotChargerCheckpoint6");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint6").GetComponent<Transform>();
            }
            else if (target.name == "Floor2Right" || target.name == "Floor2Left" || target.name == "Floor1StorageRoom")
            {
                target = GameObject.Find("RobotChargerCheckpoint6");
                destinationSetter.target = GameObject.Find("RobotChargerCheckpoint6").GetComponent<Transform>();
            }
            else if (target.name == "RobotChargerCheckpoint6" && Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
            {
                target = GameObject.Find("RobotChargePoint6");
                destinationSetter.target = GameObject.Find("RobotChargePoint6").GetComponent<Transform>();
                chargers[5].GetComponent<Charger>().SetOccupied(true);
                StartCoroutine(releaseCharger(chargers[5]));

                job = 4;
                floor = 2;
            }
        }
    }

    private void roam()
    {
        if (target.gameObject.name == "RobotElevatorFloor0")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator0").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor1")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator1").GetComponent<Animator>();
        }
        else if (target.gameObject.name == "RobotElevatorFloor2")
        {
            previousAnimator = animator;
            animator = GameObject.Find("RobotElevator2").GetComponent<Animator>();
        }

        if (Vector3.Distance(robot.transform.position, target.transform.position) < 0.5f)
        {
            if (target.gameObject.tag == "RobotTeleporter")
            {
                rcf.changeToRandomFloor(robot);
                floor = rcf.getFloor();
                //Debug.Log(floor);
                previousAnimator.SetBool("Open", false);
                animator.SetBool("Open", false);
            }

            if (floor == 0)
            {
                int nextTarget = Random.Range(0, floor0.Length);
                target = floor0[nextTarget];
                destinationSetter.target = target.transform;

                if (target.gameObject.tag == "RobotTeleporter")
                {
                    animator.SetBool("Open", true);
                }
            }
            else if (floor == 1)
            {

                int nextTarget = Random.Range(0, floor1.Length);
                target = floor1[nextTarget];
                destinationSetter.target = target.transform;
                if (target.gameObject.tag == "RobotTeleporter")
                {
                    animator.SetBool("Open", true);
                }
            }
            else
            {
                int nextTarget = Random.Range(0, floor2.Length);
                target = floor2[nextTarget];
                destinationSetter.target = target.transform;
                if (target.gameObject.tag == "RobotTeleporter")
                {
                    animator.SetBool("Open", true);
                }
            }
        }
    }


    private void moveRobot()
    {
        destinationSetter.target = robot.transform;
    }

    private int getMode()
    {
        return job;
    }

    public void setMode(int mode)
    {
        job = mode;
    }

    public void setFloor(int fl)
    {
        floor = fl;
    }

    private void setSpeed(int num)
    {
        speed = num;
        this.gameObject.GetComponent<AILerp>().speed = speed;
    }

    private void CheckEmptyChargers()
    {
        int i = 0;
        foreach(GameObject g in chargers)
        {
            chargersOccupied[i] = g.GetComponent<Charger>().GetOccupied();
            i++;
        }
    }

    IEnumerator releaseCharger(GameObject g) 
    { 
        yield return new WaitForSeconds(250);
        g.GetComponent<Charger>().SetOccupied(false);
    }
}
