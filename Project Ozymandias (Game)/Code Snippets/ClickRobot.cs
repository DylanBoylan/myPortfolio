using Pathfinding;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.Rendering.PostProcessing;
using UnityEngine.UI;

public class ClickRobot : MonoBehaviour
{
    // Start is called before the first frame update
    private PostProcessVolume volume;
    private LensDistortion lensDistortion;
    private bool charging = false;

    public GameObject camera;
    public RobotController rc;
    public HackController hc;
    public SuspicionController suspicionController;
    public SpriteRenderer sr;
    private bool goToRobot;
    public GameObject robotUI;
    public TMP_Text text;
    public TMP_Text operationText;
    private int job = 0;
    public GameObject battery;
    private Image batteryImage;
    public int robotLevel = 1;
    public float batteryDrainRate = 1.0f;
    public int rareM;
    public GameObject RM;

    private displayRareMaterials drm;

    private GameObject cameraScript;

    public GameObject lvl1;
    public GameObject lvl2;
    public GameObject lvl3;
    public GameObject lvl4;
    public GameObject lvl5;
    private TMP_Text txt;

    public bool hacked = false;

    public string name = "";
    private string[] names = {"C0RVU5", "J4M35", "L30", "B3114", "4N1K1N", "J4D3N", "D4N13L", "H4RRY", "BRUC3", "R4NDY", "CL4RK", "0L1V3R", "H3CT0R", "P3T3R", "J1MMY", "J0HN", "K1M", "SKYL3R", "S1N34D", "MAR1A", "M0N1C4", "CAR0L", "W4LT3R", "W1NST0NY", "G0BL1N", "MAGG12", "TH30", "K4T13", "J355", "3L41N3", "ANG3L1CA", "P3GGY", "S4UL G00DM4N", "M3G4TR0N", "J0EL", "4RTHUR", "C3P0", "M4A0N", "R1CHT0F3N", "D3MPS3Y", "TAK30", "NIK0L41", "MONTY", "D4RR3N"};
    
    void Start()
    {
        job = 1;
        job = 0;
        txt = transform.GetChild(1).GetComponent<TMP_Text>();
        volume = camera.gameObject.GetComponent<PostProcessVolume>();
        volume.profile.TryGetSettings(out lensDistortion);
        cameraScript = GameObject.Find("Controller").transform.GetChild(1).gameObject;
        name = names[Random.Range(0, names.Length)];
        text.text = name;
        txt.text = name;
        batteryImage = battery.transform.GetChild(0).GetComponent<Image>();
      //  robotUI = GameObject.Find("RobotUI");
        robotUI.SetActive(false);
        StartCoroutine(batteryDrain());

        lvl1.GetComponent<Image>().color = Color.green;
        lvl2.GetComponent<Image>().color = Color.red;
        lvl3.GetComponent<Image>().color = Color.red;
        lvl4.GetComponent<Image>().color = Color.red;
        lvl5.GetComponent<Image>().color = Color.red;


    }

    // Update is called once per frame
    void Update()
    {
        drm = RM.GetComponent<displayRareMaterials>();

        rareM = RM.GetComponent<displayRareMaterials>().rareMaterials;

        camera.transform.position = new Vector3(camera.transform.position.x, camera.transform.position.y, -10);
        batteryImage.color = Color.Lerp(Color.red, Color.green, battery.GetComponent<Slider>().value);
        sr.color = Color.Lerp(Color.red, Color.green, battery.GetComponent<Slider>().value);
        battery.transform.GetChild(0).transform.GetChild(0).GetComponent<TMP_Text>().text = ((int)(battery.GetComponent<Slider>().value * 100)).ToString() + "%";
        if (goToRobot) 
        {
            Debug.Log("dddd");
            while (camera.gameObject.GetComponent<Camera>().orthographicSize >= 1.5f)
            {
                if (hacked)
                {
                    robotUI.transform.GetChild(0).gameObject.SetActive(true);
                    robotUI.transform.GetChild(1).gameObject.SetActive(false);
                    robotUI.SetActive(true);
                }
                else
                {
                    robotUI.transform.GetChild(0).gameObject.SetActive(false);
                    robotUI.transform.GetChild(1).gameObject.SetActive(true);
                    robotUI.SetActive(true);
                }
                camera.gameObject.GetComponent<Camera>().orthographicSize -= .001f;
            }
            cameraScript.SetActive(false);
            camera.transform.position = Vector3.MoveTowards(camera.transform.position, new Vector3(this.gameObject.transform.position.x + 1.5f, this.gameObject.transform.position.y, this.transform.position.z), .5f);
        }

        if(Input.GetKey(KeyCode.Escape) && goToRobot == true) 
        {
            cameraScript.SetActive(true);
            goToRobot = false;
            camera.gameObject.GetComponent<Camera>().orthographicSize = 3f;
            robotUI.SetActive(false);
        }

        if (robotLevel == 1)
        {

            lvl2.GetComponent<Image>().color = Color.red;
            lvl3.GetComponent<Image>().color = Color.red;
            lvl4.GetComponent<Image>().color = Color.red;
            lvl5.GetComponent<Image>().color = Color.red;
        }
        if (robotLevel == 2)
        {
            robotLevel = 2;
            lvl2.GetComponent<Image>().color = Color.green;
            lvl3.GetComponent<Image>().color = Color.red;
            lvl4.GetComponent<Image>().color = Color.red;
            lvl5.GetComponent<Image>().color = Color.red;
        }

        else if (robotLevel == 3)
        {
            robotLevel = 3;
            lvl2.GetComponent<Image>().color = Color.green;
            lvl3.GetComponent<Image>().color = Color.green;
            lvl4.GetComponent<Image>().color = Color.red;
            lvl5.GetComponent<Image>().color = Color.red;
        }

        else if (robotLevel == 4)
        {
            robotLevel = 4;
            lvl2.GetComponent<Image>().color = Color.green;
            lvl3.GetComponent<Image>().color = Color.green;
            lvl4.GetComponent<Image>().color = Color.green;
            lvl5.GetComponent<Image>().color = Color.red;

        }

        else if (robotLevel == 5)
        {
            robotLevel = 5;
            lvl2.GetComponent<Image>().color = Color.green;
            lvl3.GetComponent<Image>().color = Color.green;
            lvl4.GetComponent<Image>().color = Color.green;
            lvl5.GetComponent<Image>().color = Color.green;
        }

        if (job == 0)
        {
            operationText.text = "Roaming";
        }
        else if (job == 1)
        {
            operationText.text = "Gathering Skill Points";
        }
        else if (job == 2)
        {
            operationText.text = "Gathering Rare Materials";
        }
        else if (job == 3)
        {
            operationText.text = "Locating Charger";
        }
        else if (job == 4)
        {
            operationText.text = "Charging";
        }
    }

    private void OnMouseDown()
    {
        Debug.Log("Clicked");
        goToRobot = true;
    }

    private void stopFollowingRobot()
    {
        goToRobot=false;
    }

    public string getName()
    {
        return name;
    }

    public void setOperation(int operation)
    {
        job = operation;
    }

    public float getBatteryPercentage()
    {
        return battery.GetComponent<Slider>().value;
    }

    public void increaseLevel()
    {
        if (rareM > 0 && robotLevel == 1)
        {
            drm.removeRareMaterial();
            robotLevel = 2;
        }

        else if (rareM > 0 && robotLevel == 2)
        {
            drm.removeRareMaterial();
            robotLevel = 3;
        }

        else if (rareM > 0 && robotLevel == 3)
        {
            drm.removeRareMaterial();
            robotLevel = 4;

        }

        else if (rareM > 0 && robotLevel == 4)
        {
            drm.removeRareMaterial();
            robotLevel = 5;
        }
    }

    public void setSpeed()
    {
        if(robotUI.GetComponentInChildren<Slider>().value == 0)
        {
            GetComponent<AILerp>().speed = 0.25f;
            batteryDrainRate = 0.2f;
        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 1)
        {
            GetComponent<AILerp>().speed = 0.5f;
            batteryDrainRate = 0.5f;
        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 2)
        {
            GetComponent<AILerp>().speed = 0.75f;
            batteryDrainRate = 0.75f;
        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 3)
        {
            GetComponent<AILerp>().speed = 1f;
            batteryDrainRate = 1f;
        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 4)
        {
            GetComponent<AILerp>().speed = 1.25f;
            batteryDrainRate = 1.25f;
            StartCoroutine(increaseSuspicion(robotUI.GetComponentInChildren<Slider>().value));
        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 5)
        {
            GetComponent<AILerp>().speed = 1.5f;
            batteryDrainRate = 1.75f;
            StartCoroutine(increaseSuspicion(robotUI.GetComponentInChildren<Slider>().value));

        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 6)
        {
            GetComponent<AILerp>().speed = 1.75f;
            batteryDrainRate = 2.5f;
            StartCoroutine(increaseSuspicion(robotUI.GetComponentInChildren<Slider>().value));

        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 7)
        {
            GetComponent<AILerp>().speed = 2f;
            batteryDrainRate = 3f;
            StartCoroutine(increaseSuspicion(robotUI.GetComponentInChildren<Slider>().value));

        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 8)
        {
            GetComponent<AILerp>().speed = 2.5f;
            batteryDrainRate = 5f;
            StartCoroutine(increaseSuspicion(robotUI.GetComponentInChildren<Slider>().value));

        }

        else if (robotUI.GetComponentInChildren<Slider>().value == 9)
        {
            GetComponent<AILerp>().speed = 4f;
            batteryDrainRate = 5f;
            StartCoroutine(increaseSuspicion(robotUI.GetComponentInChildren<Slider>().value));

        }
    }

    IEnumerator increaseSuspicion(float value)
    {
        yield return new WaitForSeconds(2);
        if (hacked)
        {
            if (value == robotUI.GetComponentInChildren<Slider>().value)
            {
                if (GetComponent<AILerp>().speed > 1f)
                {
                    suspicionController.addProgress((value) / 1000);
                    StartCoroutine(increaseSuspicion(robotUI.GetComponentInChildren<Slider>().value));
                }
            }
        }
    }

    IEnumerator batteryDrain()
    {
        yield return new WaitForSeconds(2);
        if (battery.GetComponent<Slider>().value > 0.16)
        {
            battery.GetComponent<Slider>().value -= 0.01f * (batteryDrainRate / robotLevel);
            StartCoroutine(batteryDrain());

        }
        else
        {
            //StartCoroutine(batteryRegen());
        }
    }

    public void startCharge()
    {
        if (!charging)
        {
            charging = true;
            StartCoroutine(batteryRegen());
        }
    }

    IEnumerator batteryRegen()
    {
        yield return new WaitForSeconds(0.5f);
        if (battery.GetComponent<Slider>().value < .99f)
        {
            battery.GetComponent<Slider>().value += 0.01f * robotLevel;
            StartCoroutine(batteryRegen());
        }
        else
        {
            charging = false;
            this.gameObject.GetComponent<RobotRoam>().setMode(0);
            StartCoroutine(batteryDrain());
        }
    }

    public void setHacked(bool hack)
    {
        hacked = hack;
    }

    public void startHack()
    {
        if(rc.getMaxRobots() > rc.getCurrentRobots()) 
        {
            goToRobot = false;
            camera.gameObject.GetComponent<Camera>().orthographicSize = 3f;
            robotUI.SetActive(false);

            hc.startHack(this.gameObject);
        }
    }

    public void ResetLevel()
    {
        robotLevel = 1;
    }
}
