# Jenkins + GitHub + Java 17 Setup (Ubuntu)

This guide helps you set up the following on an Ubuntu server:

- Java 17
- Maven
- Git
- Jenkins
- GitHub SSH authentication
- Jenkins to GitHub SSH connection
- Jenkins pipeline setup

---

## 1) Install Java 17, Maven, and Git

Run the following commands:

```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
sudo apt install maven -y
sudo apt install git -y
```

Verify installation:

```bash
java -version
mvn -version
git --version
```

---

## 2) Install Jenkins

Add Jenkins repository and install Jenkins:

```bash
sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2026.key

echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc]" \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt update
sudo apt install jenkins -y
```

Start and enable Jenkins:

```bash
sudo systemctl enable jenkins
sudo systemctl start jenkins
sudo systemctl status jenkins
```

> Note: Jenkins requires Java. Make sure Java 17 is installed before starting Jenkins.

---

## 3) Create SSH Key Pair for GitHub (Ubuntu User)

Generate a private/public key pair:

```bash
ssh-keygen -t rsa
```

Press Enter to save in the default location (usually `~/.ssh/id_rsa`).

This creates:

- Private key: `~/.ssh/id_rsa`
- Public key: `~/.ssh/id_rsa.pub`

Show the public key:

```bash
cat ~/.ssh/id_rsa.pub
```

Copy the output and add it to GitHub:

- GitHub -> **Settings** -> **SSH and GPG keys** -> **New SSH key**
- Paste the public key and save

---

## 4) Verify GitHub SSH Access (Ubuntu User)

Test SSH connection to GitHub:

```bash
ssh -T git@github.com
```

You should see a success message (GitHub may say authentication succeeded but shell access is not provided).

Now test cloning your repository:

```bash
git clone git@github.com:shubhamdhole97/msexcel.git
```

If clone is successful, delete the cloned folder (optional test cleanup):

```bash
rm -rf msexcel
```

---

## 5) Configure SSH for Jenkins to Access GitHub

To allow Jenkins to clone from GitHub using SSH, copy the SSH key into Jenkins user's `.ssh` directory.

### Create Jenkins SSH directory

```bash
sudo mkdir -p /var/lib/jenkins/.ssh
```

### Copy private key (important: use `cp`, not `chown`)

```bash
sudo cp ~/.ssh/id_rsa /var/lib/jenkins/.ssh/
```

### (Optional but recommended) Copy known_hosts after first GitHub SSH connection

```bash
sudo cp ~/.ssh/known_hosts /var/lib/jenkins/.ssh/ 2>/dev/null || true
```

### Set ownership to Jenkins user

```bash
sudo chown -R jenkins:jenkins /var/lib/jenkins/.ssh/
```

### Set secure permissions

```bash
sudo chmod 700 /var/lib/jenkins/.ssh
sudo chmod 600 /var/lib/jenkins/.ssh/id_rsa
sudo chmod 644 /var/lib/jenkins/.ssh/known_hosts 2>/dev/null || true
```

> **Important:** Your original step used `chown` where `cp` should be used. To copy the private key, use `cp`.

---

## 6) Verify GitHub Clone as Jenkins User

Switch to Jenkins user and test clone:

```bash
sudo -u jenkins -H bash
cd /var/lib/jenkins
ssh -T git@github.com
```

Then clone the repo:

```bash
git clone git@github.com:shubhamdhole97/msexcel.git
```

Exit Jenkins shell after verification:

```bash
exit
```

If clone is successful, you can delete the test clone:

```bash
sudo rm -rf /var/lib/jenkins/msexcel
```

---

## 7) Jenkins Initial Setup (Plugins + Pipeline)

Open Jenkins in browser:

- `http://<your-server-ip>:8080`

Unlock Jenkins (first time only):

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

Then:

1. Install **Suggested Plugins** (or select manually).
2. Ensure **Pipeline** plugin is installed.
3. (Recommended) Install **Git**, **GitHub**, and **Credentials** related plugins if not already present.

---

## 8) Create SSH Credential in Jenkins

In Jenkins:

- Go to **Manage Jenkins** -> **Credentials**
- Choose the correct scope (usually **Global**) -> **Add Credentials**

Set:

- **Kind:** `SSH Username with private key`
- **Username:** `git`
- **Private Key:** Enter directly (paste contents of `id_rsa`) or use the copied key
- **ID:** (example) `github-ssh-key`
- **Description:** GitHub SSH key for repo access

Save the credential.

---

## 9) Create and Configure Jenkins Pipeline

1. Click **New Item**
2. Enter a job name
3. Select **Pipeline**
4. Click **OK**

### Pipeline setup options

#### Option A: Pipeline script from SCM (Recommended)

- **Definition:** `Pipeline script from SCM`
- **SCM:** `Git`
- **Repository URL:** `git@github.com:shubhamdhole97/msexcel.git`
- **Credentials:** Select your SSH credential (e.g., `github-ssh-key`)
- **Branch:** `*/main` (or your branch)
- **Script Path:** `Jenkinsfile`


## 10) Final Verification Checklist

- [ ] Java 17 installed and verified
- [ ] Maven installed and verified
- [ ] Git installed and verified
- [ ] Jenkins installed and running
- [ ] GitHub SSH key added in GitHub account
- [ ] SSH access verified with `ssh -T git@github.com`
- [ ] Repo clone works from Ubuntu user
- [ ] Repo clone works from Jenkins user
- [ ] Jenkins SSH credential created
- [ ] Pipeline job created and configured

---

