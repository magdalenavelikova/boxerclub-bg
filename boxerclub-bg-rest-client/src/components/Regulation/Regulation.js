import { useEffect } from "react";
import { Accordion, Card, Container } from "react-bootstrap";

export const Regulation = ({ regulation }) => {
  const head = require("../../assets/standard/head.png");
  const teeth = require("../../assets/standard/teeth.gif");
  const eyes = require("../../assets/standard/eyes.png");
  useEffect(() => {}, [regulation]);

  return (
    <Container className='mt-4 p-2 justify-content-center'>
      <Accordion alwaysOpen activeKey={regulation}>
        <Accordion.Item eventKey='standard'>
          <Accordion.Button className='accordion-button bg-secondary'>
            FCI-Standard N°144 / 09.07.2008 / GB
          </Accordion.Button>
          <Accordion.Body>
            <Card className='p-1 border-0' variant='light'>
              <Card.Header className='mb-3 text-center'>
                <Card.Title>BOXER</Card.Title>
                <Card.Subtitle>(Deutscher Boxer)</Card.Subtitle>
                <Card.Img className='mt-3 ' src={head} alt='Card image' />
              </Card.Header>
              <Card.Text>
                TRANSLATION: Mrs C. Seidler, revised by Mrs Sporre-Willes and R.
                Triquet. Revised by J. Mulholland 2008.
              </Card.Text>
              <Card.Text>ORIGIN: Germany.</Card.Text>
              <Card.Text>
                DATE OF PUBLICATION OF THE ORIGINAL VALID STANDARD: 01.04.2008.
              </Card.Text>
              <Card.Text>
                UTILIZATION: Companion, Guard and Working Dog.
              </Card.Text>
              <Card.Text>
                CLASSIFICATION F.C.I.: Group2Pinscher and Schnauzer- Molossoid
                breeds - Swiss Mountain and Cattle Dogs. Section2.1 Molossoid
                breeds, mastiff type. With working trial.
              </Card.Text>
              <Card.Text>
                BRIEF HISTORICAL SUMMARY: The small, so called Brabant
                Bullenbeisser is regarded as the immediate ancestor of the
                Boxer. In the past, the breeding of these Bullenbeissers was in
                the hands of the huntsmen, whom they assisted during the hunt.
                Their task was to seize the game put up by the hounds and hold
                it firmly until the huntsman arrived and put an end to the prey.
                For this job the dog had to have jaws as wide as possible with
                widely spaced teeth, in order to bite firmly and hold on
                tightly. A Bullenbeisser which had these characteristics was
                best suited to this job and was used for breeding. Previously,
                only the ability to work and utilization were considered.
                Selective breeding was carried out which produced a dog with a
                wide muzzle and an upturned nose.
              </Card.Text>
              <Card.Text>
                GENERAL APPEARANCE: The Boxer is a medium sized, smooth coated,
                sturdy dog of compact, square build and strong bone. His muscles
                are taut, strongly developed and moulded in appearance. His
                movement is lively, powerful with noble bearing. The Boxer must
                be neither cumbersome nor heavy, nor light or lacking in body
                substance.
              </Card.Text>
              <Card.Text>
                IMPORTANT PROPORTIONS:
                <br /> a) Length of body / Height at withers : Square build,
                which means that the horizontal line of the back is
                perpendicular to the vertical line passing through the point of
                shoulder and to the other vertical line passing through the
                point of buttock, thus defining a square outline.
                <br /> b) Depth of brisket / Height at withers : The chest
                reaches to the elbows. Depth of chest is half the height at
                withers. <br />
                c) Length of nose bridge / Length of head : Length of nose
                bridge in relation to skull should be 1 : 2 (measured
                respectively from tip of nose to inner corner of eye and from
                inner corner of eye to occiput).
              </Card.Text>
              <Card.Text>
                BEHAVIOR / TEMPERAMENT: The Boxer should be fearless
                self-confident, calm and equable. Temperament is of the utmost
                importance and requires careful attention. Devotion and loyalty
                towards his master and his entire household, his watchfulness
                and self-assured courage as a defender are famous. He is
                harmless with his family but distrustful of strangers. Happy and
                friendly in play, yet fearless in a serious situation. Easy to
                train on account of his willingness to obey, his pluck and
                courage, natural keenness and scent capability. Undemanding and
                clean, he is just as agreeable and appreciated in the family
                circle as he is as a guard, companion and working dog. His
                character is trustworthy, with no guile or cunning, even in old
                age.{" "}
              </Card.Text>
              <Card.Text>
                HEAD: This gives the Boxer his characteristic look. Must be in
                good proportion to the body and appear neither too light nor too
                heavy. Muzzle should be as broad and powerful as possible. The
                harmony of the head depends on the balance between muzzle and
                skull. From whichever direction the head is viewed, from front,
                above or sideways, the muzzle must always be in the right
                proportion to the skull i.e. it must never appear too small. It
                should be clean, not showing any wrinkle. However, natural folds
                are formed in the cranial region when alerted. From root of
                nose, folds are always indicated running in a downward direction
                on both sides. The dark mask is confined to the muzzle and must
                be in sharp contrast to the color of the head so that the face
                does not appear sombre.{" "}
              </Card.Text>
              <Card.Text>
                CRANIAL REGION: <br />
                Skull : The cranial region should be as lean and angular as
                possible. It is slightly arched, neither too round and short,
                nor flat; nor should it be too broad. Occiput not too
                pronounced. Furrow in forehead only slightly marked, must not be
                too deep, especially between the eyes. <br />
                Stop : The forehead forms a distinct stop with the bridge of
                nose. Bridge of nose must not be forced back into the forehead
                as in the Bulldog, nor should it be downfaced.{" "}
              </Card.Text>
              <Card.Text>
                FACIAL REGION:
                <br /> Nose : Nose is broad and black and only slightly turned
                up with wide nostrils. Tip of nose is placed slightly higher
                than root of nose. <br />
                Muzzle : The muzzle is powerfully developed in three dimensional
                volume, neither pointed nor narrow, nor short or shallow. Its
                appearance is influenced by:
                <br />
                a) Shape of jaw. <br />
                b) Position of canine teeth. <br />
                c) Shape of lips. The canines must be placed as far apart as
                possible and must be of good length, making the front of the
                muzzle broad, almost square and forming a blunt angle with
                bridge of nose. <br />
                In front, the edge of the upper lip rests on the edge of the
                lower lip. The part of the lower jaw with lower lip curved
                upwards, called the chin, must not markedly protrude over upper
                lip, seen from front. Nor should it be hidden by the upper lip
                but should be well defined from front and side. The canines and
                incisors of the lower jaw must not be visible when mouth is
                closed, neither should the tongue show. Median groove in the
                upper lip (philtrum) is clearly visible.
                <br /> Lips: The lips complete the shape of the muzzle. The
                upper lip is thick and padded and fills the space formed by the
                undershot lower jaw; it is supported by the lower canines.
                <br /> Jaws/Teeth: The lower jaw protrudes beyond the upper jaw
                and is curved slightly upwards. The Boxer is undershot. The
                upper jaw is broad where it joins the cranial region, tapering
                only slightly towards the front. The teeth are strong and
                healthy. The incisors are as even as possible, set in a straight
                line. Canines wide apart and of good size.
              </Card.Text>
              <Card.Img className='mt-2 mb-2' src={teeth} alt='Card image' />
              <Card.Text>
                Cheeks : Cheeks are developed in proportion with the strong jaws
                without markedly bulging. They merge with the muzzle in a slight
                curve.
                <br /> Eyes : The dark eyes are neither too small nor protruding
                or deep set. Their expression conveys energy and intelligence
                and must not be threatening or piercing. Eye rims must be dark.{" "}
              </Card.Text>
              <Card.Img className='p-5' src={eyes} alt='Card image' />
              <Card.Text>
                <b>EARS:</b> The natural ears are of appropriate size. They are
                set on wide apart on highest part of skull. In repose they lie
                close to the cheeks and turn forward with a definite crease,
                especially when the dog is alert. <br /> <b>NECK:</b> Topline
                runs in an elegant arch from the clearly marked nape to the
                withers. It should be of ample length, round, strong and
                muscular. <br /> <b>BODY:</b> Square body resting on sturdy,
                straight legs. <br /> <b>WITHERS: </b>Should be marked. Back :
                Including loin should be short, firm, straight, broad and
                muscular. <br /> <b>CROUP:</b>Slightly sloping, broad and only
                slightly arched. Pelvis should be long and broad, especially in
                bitches. <br /> <b>CHEST:</b> Deep, reaching to elbows. Depth of
                chest is half the height at withers. Well developed forechest.
                Ribs well sprung but not barrel-shaped, extending well to the
                rear.
                <br /> <b>UNDERLINE:</b> Running towards rear in elegant curve.
                Short, taut flanks slightly tucked up. <br />
                <b>TAIL:</b> Set on high rather than low. The tail is of normal
                length and left natural.
              </Card.Text>
              <Card.Text>
                <b>LIMBS</b>
                <br />
                FOREQUARTERS : Front legs, seen from front, must stand parallel
                and have strong bone.
                <br />
                Shoulders : Long and sloping, connected firmly to body. Should
                not be too loaded.
                <br />
                Upper arm : Long, making a right angle to shoulder blade.
                <br />
                Elbows : Neither too close to side of chest nor turned out.
                <br />
                Forearm : Vertical, long, lean and muscled.
                <br />
                Carpus (wrist) : Strong, well defined, but not exaggerated.
                <br />
                Metacarpus (Pastern) : Short, almost perpendicular to ground.
                <br />
                Front feet : Small, round, tight, well cushioned and hard pads.{" "}
                <br />
                HINDQUARTERS : Very muscular, the muscles brick hard and visible
                under the skin. <br />
                Hindlegs : Seen from rear : straight.
                <br />
                Thigh : Long and broad. Angles of hip and knee are open but as
                little as possible. <br />
                Knee (Stifle) : When dog is standing, should reach sufficiently
                forward so that it would touch a perpendicular line from point
                of hip to ground.
                <br />
                Lower thigh : Very muscular.
                <br />
                Hock : Strong and well defined but not exaggerated. Angle
                approximately 140 degrees.
                <br />
                Metatarsus (Rear Pastern) : Short with slight inclination,
                95-100 degrees to the ground. <br />
                Hind feet : Slightly longer than front feet, tight; well
                cushioned and hard pads.
                <br />
                GAIT / MOVEMENT: Lively, full of strength and nobility.
                <br />
                SKY: Dry, elastic without any wrinkles.
                <br />
                <b>COAT</b>
                <br />
                HAIR: Short, hard, glossy and close fitting.
                <br />
                COLOUR: Fawn or brindle : Fawn comes in various shades from
                light fawn to dark deer red but the most attractive shades are
                in the middle range (red fawn).Black mask. The brindle variety :
                fawn background of varying shades has dark or black stripes
                running parallel to ribs.Stripes must contrast distinctly to
                ground colour.White markings should not be discarded.They can be
                quite pleasant.
                <br />
                SIZE AND WEIGHT:
                <br />
                Height at the withers : Dogs :57-63 cm.
                <br />
                Females :53-59 cm.
                <br />
                Weight : Dogs : over30 kg when height at withers is ca 60 cm.
                <br />
                Bitches : about 25 kg when height at withers is ca 56 cm.
                <br />
                FAULTS: Any departure from the foregoing points should be
                considered a fault and the seriousness with which the fault
                should be regarded should be in exact proportion to its degree
                and its effect upon the health and welfare of the dog.
                <br />
                • Behaviour / Temperament : Lack of spirit.
                <br />
                • Head : Lack of nobility and typical expression, sombre face,
                Pinscher or Bulldog type head. Dribbling, showing of teeth or
                tongue. Muzzle too pointed or slight. Bridge of nose falling
                away. Leather or weather nose, pale nose leather. So called,
                “hawk eye”, lack of pigment in haw. In uncropped ears : Flying,
                half erect or erect ears, rose ears. <br />
                Wry jaw, slanting teeth, incorrect position of teeth, poorly
                developed teeth and unsound teeth due to illness.
                <br />
                • Neck : Short, thick and throaty.
                <br />
                • Body : Front too broad or low to the ground. Sagging body,
                roach or sway back. Lean, long, narrow, sagging loin, loosely
                coupled body.
                <br />
                Arched loin, croup falling away. Narrow pelvis, hollow flanks,
                pendulous belly.
                <br />
                • Tail : Low set on, kink tail.
                <br />
                • Forequarters: French front, loose shoulders, loose elbows,
                weak pastern, hare foot, flat, splayed feet.
                <br />
                • Hindquarters : Weak muscles. Too much or too little
                angulation, down on hocks, barrel hocks, cow hocks, narrow
                hocks, dewclaws, hare foot, flat, splayed feet.
                <br />
                • Movement : Waddling, insufficient reach, pacing, stilted gait.
                <br />
                • Colour of coat : Mask extending beyond muzzle. Stripes
                (brindling) too close together or too sparse.
                <br />
                Sooty ground colour. Mingled colours. Unattractive white
                markings such as a entirely white head or white on one side of
                the head. Other colours and white markings exceeding one third
                of the ground colour.
                <br />
                <b>ELIMINATING FAULTS</b>
                <br />
                Aggressive or overly shy.
                <br />
                Naturally stumpy tail.
                <br />
                Any dog clearly showing physical or behavioural abnormalities
                shall be disqualified.
                <br />
                N.B. : Male animals should have two apparently normal testicles
                fully descended into the scrotum.
              </Card.Text>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey='AD'>
          <Accordion.Button className='accordion-button bg-secondary'>
            Endurance Test
          </Accordion.Button>
          <Accordion.Body>
            <Card className='p-1 border-0' variant='light'>
              <Card.Text>
                <b>General Information</b>
                <br />
                The AD is an endurance test used for show and breed
                requirements. The dog must run beside its handler for a distance
                of 20 kilometers (about 12.5 miles.) It will test the dogs
                physical strength and make sure the dog is physically able to
                endure a certain amount of physical stress without showing
                significant fatigue. A short obedience exercise will follow the
                running test.
                <br />
                <b>Requirements for Entry</b>
                <br />
                The dog must be at least 16 months old to trial and dogs more
                than 6 years old are excused from having to complete this part
                of the breed survey. All dogs must be registered, and have their
                score book, pedigree and registration papers on hand the day of
                trial. The dog must be healthy and in good condition. Sick dogs,
                weak dogs, bitches in heat, bitches in whelp and nursing bitches
                are not allowed to participate. The judge and trial secretary
                will verify that each dog is in good condition. Dogs that
                present a tired or listless expression are to be disqualified.
                The handler will conduct him/herself in a sporty manner.
                Unsportsmanlike behavior can result in disqualification from the
                examination. All decisions made by the judge are final.
                Participation in the AD test is voluntary. If during the course
                of the test, the dog or handler is injured in any way, the
                United Schutzhund Clubs of America, the SV and the local club
                cannot be held responsible. <br />
                <b>Scoring</b>
                <br />
                The AD award is not a training degree but is for show and breed
                requirements. No points or awards will be given for the
                examination, only the rating of "Passed" or "Not Passed". Only
                dogs that pass will receive the AD certification.
                <br />
                <b>Terrain</b>
                <br />
                The test should preferably be run on streets or roads of varied
                terrain such as asphalt or dirt.
                <br />
                <b>The Test</b>
                <br />
                A.) The Running Exercise - The dogs will be run a total of 20
                kilometers (about 12.5 miles) at an average speed of 12 to 15
                kilometers per hour (7.7 to 9.5 miles per hour).
                <br />
                The dog must be kept on leash on the right hand side of the
                handler and move in a normal trot next to the bicycle. Overly
                fast running is to be avoided. The leash must be sufficiently
                long to give the dog the ability to adjust to any changes in
                speed. Slight pulling or forging is not faulty but continually
                failing behind is faulty. There will be a 15 minute rest period
                after the dogs have completed 8 kilometers (5 miles). During the
                rest period, the judge will examine the dogs for fatigue. Tired
                dogs will be removed from the test. There will be a 20 minute
                rest period at the end of 15 kilometers (9.4 miles). The judge
                will again check the dogs. Dogs that are tired or dogs with sore
                feet will be removed from the test. Dogs will be allowed to move
                about freely during the rest periods but should be on leash. The
                last 5 kilometers will be run and then there will be a 15 minute
                rest period. The judge will again determine if the dog
                demonstrates tiredness or sore feet due to the running.
                <br />
                The judge and the trial secretary should accompany the dogs on a
                bicycle or car. The judge will make notes about the condition of
                the dogs and all deficiencies will be noted. It is necessary
                that a car follow the handlers and dogs so that tired or sore
                dogs can be transported. Dogs that lack the proper spirit and
                hardness and those that show fatigue and dogs that cannot keep
                up the required speed or need extra time cannot pass the
                examination.
                <br />
                B.) Obedience - After the running exercise and when requested by
                the judge, the handlers will assemble with their dogs in the
                heeling position. Under the direction of the judge, the handlers
                and dogs will go through the heeling exercise (on or off leash)
                according to the Schutzhund rules. No gun shots are to be fired.
                In addition, the judge must verify the psychological condition
                of the dogs. The method is left up to the judge
              </Card.Text>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey='BH'>
          <Accordion.Button className='accordion-button bg-secondary'>
            Companion Dog
          </Accordion.Button>
          <Accordion.Body>
            <Card className='p-1 border-0' variant='light'>
              <Card.Text>
                This test was developed as a preliminary character evaluation.
                It was designed to keep aggressive, sharp, shy, or nervous dogs
                from participating in the sport. DVG rules say dogs of all sizes
                and breeds are eligible; the minimum age is 12 months. The only
                allowable collar is a chain type "choke" collar, and the lead,
                when used, is attached to the dead ring. Generally, scores or
                points are not announced, rather the judge evaluates whether a
                handler/dog team have passed. Part A must be passed for the team
                to do Part B.
                <br />
                <b>Part A</b>
                <br />
                The Obedience phase of the BH is eventually the same as the
                obedience in SchH 1 but without the retrieving exercise.
                <br />
                Obedience exercises should show the bond between thehandler/dog
                team. The dog should show a willingness to work and pure joy to
                be out on the field. The obedience exercises are done in groups
                of twodogs/handlers. Both teams report to the judge and state
                their name and their dogs name. The judge then directs each team
                to their appropriate locations. While one team is performing the
                heeling exercises, the other team is doing the long down. Any
                exercises on leash should be done with a loose lead. The leash
                is to be held in the left hand. Attach leash to the dead ring of
                a choke (fursaver) type collar. Leather and prong collars are
                not permitted.
                <br />
                Heeling on leash (15 points) Singly and within a group.
                <br />
                The dog must stay close and keep his shoulder blades "level"
                with the handlers' knees. The dog must not forge ahead, move to
                the side or lag to the rear. Upon a halt the dog must, on his
                own, go to the sitting position. The only voice command given
                should be at the start of exercise and when changing paces.
                <br />
                Starting from the basic heeling position, dog sitting at your
                left side give one voice command of heel (the dog should
                willingly follow at this time) proceed forward in a straight
                line for 40-50 paces without stopping. Do a complete turnabout
                and come back 10-15 paces. Give the heal command and without
                hesitation do a running heel for 10-15 paces followed by a slow
                heel of 10-15 paces. Return to a normal pace for 10-15 more
                paces. The "fuss" or "heel" command can be given at the
                beginning of each change of pace. You then make a right turn,
                heel 20 paces, make another right turn, heel 20 paces, make an
                about turn, heel 10-15 paces and halt. Heel forward 10-15 more
                paces and make a left turn. You can then proceed directly to the
                group. The judge will direct the handler through a group of at
                least 4 people, who are expected to mingle about. You must make
                at least 1 left turn and 1 right turn around at least 2 people
                and halt close to one of the group. (This is basically a figure
                8.) You then leave the group, halt (handler may praise dog) and
                remove the leash
                <br />
                Heeling off leash (15 points) Singly and within a group.
                <br />
                When requested by the judge, the leash will be removed while in
                the basic position. The handler moves through the group with the
                dog freely heeling. After demonstrating at least one halt, the
                handler and dog leave the group and perform the heeling
                exercises that were performed on leash. While the dog and
                handler are performing the off-leash exercises, at least 2 gun
                shots (6 - 9 mm) are to be fired (not while moving in the group)
                and the dog must remain indifferent to the noise. Special
                emphasis is placed on indifference to the gun. If the judge
                deems the dog to be insecure or should the dog run from the
                shot, the judge may excuse the dog from further participation.
                <br />
                Sit (10 points)
                <br />
                From the basic heeling position the handler and free heeling dog
                proceed in a straight line. After at least ten paces, the
                handler issues the voice command to sit - the dog should quickly
                come to a sit position. The handler shall continue for at least
                30 paces without interrupting pace or direction, then stop and
                turnaround to face the dog. At the direction of the judge, the
                handler returns to the right side of the dog.
                <br />
                Down With Recall (10 points)
                <br />
                From the basic heeling position the handler and free heeling dog
                proceed in a straight line. After at least ten paces, the
                handler issues the voice command to down - the dog should
                quickly come to a down position. The handler shall continue for
                at least 30 paces without interrupting pace or direction, then
                stop and turn around to face the dog. At the direction of the
                judge, the handler shall recall the dog. The dog should come to
                the handler with a spirited and swift motion and sit close in
                front. Upon a "heel" command, the dog should quickly come to a
                sit position next to the handler.
                <br />
                Long Down (10 Points) Under Distraction
                <br />
                Prior to the start of the obedience exercises of another dog,
                the handler commands the dog into a down position at a spot
                designated by the judge. The handler moves approximately 40
                paces away within sight of the dog. The handler remains quiet
                with his back to the dog. The dog must remain in the down
                position without additional influences from the handler until
                the other dog concludes the first 6 exercises. The finish will
                be like the Go Ahead and Down, above
                <br />
                <b>Part B </b>
                <br />
                This consists of tests to evaluate the dog's ability to function
                in heavy traffic. These exercises are to be conducted in the
                open with areas with some traffic, but not inconveniencing the
                general public. For this reason, only dogs that pass Part A may
                take this part of the test. It is a time consuming test, and a
                maximum of 15 dogs per day per judge may be tested. There is no
                point allocation per exercise; the judge will evaluate the dog's
                performance and its ability to do the exercises well.
                <br />
                Ability to Perform in Traffic The handler and judge are
                instructed to proceed on leash down a designated walkway,
                street, or roadway. The dog should heel willingly on a loose
                leash. The dog must act within difference toward pedestrian and
                other traffic including joggers and pedestrians. After
                negotiating the traffic on the roadway, the handler and dog
                proceed to the judge and stop, shake hands, converse. The dog is
                expected to ignore the judge and may stand, sit, or lay down
                quietly.
                <br />
                Behavior of the Dog Under Extreme Traffic Conditions The dog and
                handler now move through rather heavy and noisy pedestrian
                traffic. The handler must stop twice the first time ordering the
                dog to sit and the second time to lay down. The dog must remain
                calm and undisturbed.
                <br />
                Behavior of the Dog Left Alone During Traffic Conditions The
                handler secures the dog to a suitable tie off and moves out of
                sight of the dog, remaining out of sight for approximately two
                minutes. Another dog and handler team will pass within five
                paces of the secured dog which must remain calm during this
                exercise.
              </Card.Text>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey='IPO1'>
          <Accordion.Button className='accordion-button bg-secondary'>
            IPO I
          </Accordion.Button>
          <Accordion.Body>
            <Card className='p-1 border-0' variant='light'>
              <Card.Text>
                <b>Part A: TRACKING</b>
                <br />
                Maximum score: 100 points
                <br />
                Verbal command: "search/such" [ENGLISH WORD/GERMAN WORD]
                <br />
                -staying on the track = 80 pts
                <br />
                -two articles found = 20 pts
                <br />
                The track should be between 350 - 400 paces in length, laid by
                the handler, with two right angle corners and at least 20
                minutes old. The dog is placed out of sight when the handler
                lays the trial. The judge will advise the handier about the
                pattern of the trial. The handier will mark the start with a
                stake or marker. The handier will remain on the right side of
                the stake for approximately one minute without trampling or
                marking the starting point. Two articles of approximately 15
                centimeters (cm) in length, approximately 5-6 cm in width and
                approximately 2-3 cm in thickness are to be handed to the
                handler approximately 15 minutes prior to the laying of the
                track. Both articles are to be placed on the track by the
                handler, the first article in the middle of the second leg and
                the second article at the end of the track.
                <br />
                Before the start, the handler will report to the judge with the
                dog on leash or equipped for tracking. The dog should sit at
                heel during the reporting process. The handier will advise the
                judge whether the dog will pick up or point out the articles. If
                a dog is trained to pick up the articles, it is permissible to
                stand, sit or return to the handler, It is not necessary for the
                pick up exercise to be performed the same both times. Both
                picking up and pointing out together is faulty. If a dog points
                out the articles, the exercise can be performed by laying down,
                sifting or standing and again, it is not necessary to perform
                both indications in the same manner. It is faulty if the dog
                points out one article and picks up the other or drops the
                article during the pick up indication.
                <br />
                It is up to the discretion of the handler how the dog is to be
                started and whether to track on a leash or to track free without
                a leash. The tracking leash must be 10 meters in length and must
                be completely let out during the tracking phase. Should a
                handler choose to track without a tracking leash, the dog must
                be followed at a distance of 10-12 meters, The track should be
                worked out by the dog in a quiet manner so that the handler can
                follow at a convenient pace. When the articles are found they
                are to be taken by the handier and shown to the judge by raising
                an arm, The articles are to be returned to the judge after the
                completion of the track.
                <br />
                Dispensing food for praising purposes is not permitted while on
                the track. After the completion of the track, the handler
                reports to the judge with the dog sitting at heel to present the
                articles. The judge may stop the evaluation if, after 15 minutes
                on the track, the end has not been successfully reached.
                <br />
                <b>Part B: OBEDIENCE</b>
                <br />
                Maximum score: 100 points
                <br />
                Each individual exercise begins and ends with the basic
                position.
                <br />
                The judge gives the signal to begin each exercise. All further
                movements, such as turns, halt, change of pace etc. will be
                carried out without signals from the judge; however, the handler
                is permitted to request the judge to give commands (signals) for
                all of these movements.
                <br />
                The transition from fast pace to slow pace must be shown without
                putting any normal-pace steps in between. The "left-about-tums"
                may be made in either of two ways.
                <br />
                The "halt" must be performed as shown on the IPO Diagram for
                Obedience Exercises (found at the end of these rules.)
                <br />
                When the dog is commanded to go to "heel" from the "front"
                position, the dog may do so either by going around the handier
                or by going directly to heel ("flip" or "military" finish.)
                <br />
                1. Heeling on leash and impartiality (15 pts)
                <br />
                Verbal command: "heel/Fuß"
                <br />
                The handler stands with his dog sifting beside him, on leash, in
                the basic position. When the handler gives the verbal command
                "heel/Fug", the dog must go with him willingly. At the start of
                the exercise, the handier and dog must go straight out 40-50
                paces without stopping, make an about-turn, and after 10-15
                paces must show the fast pace and the slow pace (at least 10
                paces each.) Then, in normal pace, the handler and dog must
                perform at least one right turn, one left turn, and one
                about-turn. The dog must remain always on the left side of the
                handler, with his shoulder-blade level with the handler's knee;
                the dog may not forge, lag or travel out wide from the handler.
                <br />
                The "about-turn" is to be shown as a left-about-turn. The
                handler may give the verbal command "heel/Fug" only when
                starting forward and when changing pace. When the handler stops,
                the dog must sit quickly without influence from the handler.
                During this procedure, the handler may not change his basic
                position and especially may not move to accommodate a
                crooked-sifting dog. The lead must be held in the handler's left
                hand throughout the exercise, and it must hang slack. Upon the
                judge's signal, the handler takes his dog through a group of at
                least four people. The handler must stop once in the group. The
                group members are to move about one another. Lagging, forging,
                heeling wide, and hesitation of the handler at the turns are
                faulty.
                <br />
                2. Free heeling (20 pts)
                <br />
                Verbal command: "heel/Fuß"
                <br />
                At the judge's instruction, the dog is unleashed while in the
                basic position. The handier hangs the lead around his shoulder
                or puts it in his pocket, and then proceeds immediately into the
                group with his free-heeling dog, stopping at least once in the
                group. After leaving the group, the handler briefly assumes the
                basic position, and then begins the free-heeling routine,
                following the same pattern as in the "heeling-on-leash"
                exercise.
                <br />
                While the handier is performing the "free-heeling" exercise (but
                not while he is going through the Group), two shots (caliber 6-9
                mm) are to be fired, The dog must remain indifferent to the
                gunshots.
                <br />
                Any dog showing gun-shyness will be immediately excused from the
                trial. It is definitely faulty if the dog shows a desire to
                attack at the sound of the shots, even though he remains under
                the handler's control. Only the dog that is indifferent to the
                gunshots will receive full points. Procedure:
                <br />
                Special merit is placed upon indifference to gunshots. The shots
                are fired at a distance of about 15 paces (45), two shots, with
                an interval of five seconds between them. If the dog runs away
                when the shots are fired, he is excused from the trial. If the
                judge believes that he detects gun-sensitivity in a dog, it is
                his prerogative to test the dog further by having more shots
                fired. The test for indifference to gunshots may only be
                conducted during the "free-heeling" and the "down under
                distraction" exercises.
                <br />
                3. Sit exercise (10 pts)
                <br />
                Verbal command: "sit/sitz"
                <br />
                From the basic position, the handler goes straight forward with
                his free-heeling dog. After at least ten paces, the handler
                gives the verbal command "sit/sitz", and the dog must sit
                quickly without the handler altering his pace or looking
                backwards. The handier goes on another 30 paces, then stops and
                turns to face his dog. At the judge's signal, the handler
                returns to the dog and takes up the basic position on the dog's
                right side, Up to 5 points will be deducted if the dog lies down
                or remains standing instead of sitting at the command.
                <br />
                4. Down with recall (10 pts)
                <br />
                Verbal commands: "down/platz"; "come/hier"; "heel/Fuß"
                <br />
                From the basic position, the handier gives the verbal command
                "heel/Fuß and proceeds straight forward with his dog. After at
                least ten paces, the verbal command "down/platz" is given and
                the dog must quickly fie down. Without any other influence on
                the dog and without turning around, the handler goes on a
                further 30 paces, turns immediately to face his dog, and remains
                standing still.
                <br />
                At the judge's signal, the handler recalls his dog. The dog must
                come in rapidly and happily and must sit close in front of the
                handier. On the verbal command "heel/Fuß", the dog must quickly
                go to sit beside the handler. If the dog remains standing, or
                sits, but comes in perfectly, up to 5 points may be deducted.
                <br />
                5. Retrieving a handler's article on flat ground (10 pts)
                <br />
                Verbal command: "fetch/bring"
                <br />
                The dog sits free next to the handier. The handier throws an
                article about ten paces (30) away. Upon the single verbal
                command "fetch/bring", the dog must run rapidly to the thrown
                article, immediately pick it up, and return to the handier at a
                fast pace. The dog must sit close in front of the handler and
                must hold the article in his grasp until the handler, after a
                brief pause, takes the article from the dog with the verbal
                command "out/aus."
                <br />
                On the verbal command "heel/Fuß", the dog must rapidly go and
                sit on the left side of the handler. A wooden dumbbell may be
                used instead of a handier's article. The handier must remain in
                the basic position until the dog has relinquished the article
                and is sifting on the handler's left side in the "finish"
                position.
                <br />
                Scoring:
                <br />
                If the dog drops the article, plays with it or mouths it, up to
                4 points may be deduct-' ed. Changing of basic position by the
                handler can cause a deduction of up to 3 points. If the dog does
                not retrieve the article, the exercise will be scored 0 points.
                <br />
                6. Retrieving a handler's article by a clean jump over a
                brush-hurdle I meter (39") high, 1.5 meters ( 5') wide (15 pts)
                <br />
                Verbal commands: "jump/hopp"; "fetch/bring"
                <br />
                The handler positions himself at an appropriate distance from
                the hurdle with his dog sitting free beside him. Instead of a
                handler's article, the handier may throw a wooden dumbbell over
                the hurdle. On the verbal commands "jump/hopp", "fetch/bring"
                the dog must perform a clean jump over the hurdle without
                disturbing it, immediately pick up the article, return over the
                hurdle, sit close in front of the handier, and hold the article
                in his grip until the handier, after a brief pause, takes the
                article from him with the verbal command "out/aus". At the
                verbal command "heel/Fuß, the dog must go rapidly to sit beside
                the handier. The verbal command "fetch/bring" must be given
                before the dog has reached the article. Scoring:
                <br />
                Point-deductions are as follows: For light touching, up to 2
                points; for heavy touching and light stepping on the jump, up to
                3 points; for heavy stepping on the jump, dropping the article,
                playing with it or mouthing it, up to 4 points.
                <br />
                Outward jump done, return jump refused, article not retrieved 0
                pts
                <br />
                If the article thrown by the handler lands over to one side,
                through poor throwing or strong side-wind, the handler may ask
                the judge for permission to go pick up the article and re-throw
                it. No point-deduction will result from this.
                <br />
                If there is handler-help on the jumps, even without the handler
                changing his basic position, points will be deducted. More
                points will be lost if the handler leaves his basic position to
                help the dog on the jumps. Banging on the hurdle, in combination
                with leaving the basic position, is considered such an enormous
                help that no points can be given for either the outward or the
                return jump. The handler must remain in the basic position until
                the dog has relinquished the article and returned quickly to sit
                beside the handier on the verbal command "heel/Fuß". If several
                different hurdles are available, all the dogs must jump the same
                one.
                <br />
                7. Send-away with down (10 pts)
                <br />
                Verbal commands: "go out/voraus"; "down/platz"; "sit/sitz"
                <br />
                On the judge's signal, the handier with his free-heeling dog
                proceeds several steps straight ahead in the direction he has
                been instructed to go. Simultaneously the handler lifts his arm
                and gives the verbal command "go out/voraus" and stops still.
                The dog must go out at a fast pace at least 25 paces (75') in
                the indicated direction, and must lie down immediately upon the
                verbal command "down/platz." The handler may keep his arm raised
                high, to show the direction, until the dog has downed. On the
                judge's signal, the handler picks up his dog by returning to the
                right side of the dog and giving the verbal command "sit/sitz".
                Procedure:
                <br />
                Repeated raising and lowering of the arm is not allowed, The dog
                is to go out in a straight direction, but minor deviations are
                not faulty. Strong deviation, too short a go-out, too early or
                hesitant lying down, and standing up of the dog on the handler's
                return will result in point loss.
                <br />
                8. Down under distraction (1O pts)
                <br />
                Verbal commands: "down/platz"; "sit/sitz"
                <br />
                Before the start of the obedience exercises of another dog, the
                handler downs his dog at a distance of about 40 paces, without
                leaving the leash or any type of article with the dog. Remaining
                within the dog's sight, the handler goes to a spot about 40
                paces from the dog, without turning around, and there remains
                quietly standing with his back to the dog. The dog must remain
                lying there, without any influence from the handier, until the
                other dog working on the field has completed exercises 1-6.
                After exercise 6, the "downed" dog will be picked up by the same
                procedure as in exercise 7 above.
                <br />
                Procedure:
                <br />
                The handler must stand quietly with his back to his dog in that
                spot on the trial ground where he was instructed to stand, until
                the judge instructs him to pick up his dog. Restless behavior of
                the handler, and other hidden help, as well as too early getting
                up of the dog when the handler returns, are faulty.
                <br />
                If the dog stands or sits, but remains on the spot where he was
                put down, a partial score will be given. If, prior to the
                completion of exercise 3 by the dog on the field, the "downed"
                dog moves a distance of more than 3 meters (10') from where he
                was put down, the exercise will receive 0 points. If the
                "downed" dog leaves his position after the other dog completes
                exercise 3, he will receive partial points. If the dog comes to
                meet the handler as the handler goes to pick him up, up to 3
                points are deducted.
                <br />
                <b>Part C: PROTECTION WORK</b>
                <br />
                Maximum Score: 100 points
                <br />
                1. Searching for the Helper (5 pts)
                <br />
                Six blinds are to be set up in a staggered fashion, three on
                each side, along the length of an area measuring about 100
                meters (300 ft.) long and about 80 meters (240 ft.) wide. A
                Helper with a full protection suit, protection sleeve and soft
                stick is placed in the last (sixth) blind, out of sight of the
                dog.
                <br />
                The handler (HF) positions himself, with his dog off leash
                sifting beside him, on an imaginary midline at the level of the
                fifth blind. By raising one arm high into the air, the HF
                signals the judge that he is ready to begin the work. Upon a
                signal from the judge, the HF begins the Protection Work. Upon
                the handler giving short voice commands and hand signals with
                the right or left arm, which may be repeated, the dog must
                quickly leave the handier and run to and around the last two
                blinds in order. The HF must move along the imaginary midline,
                and may not leave this position during the search for the
                Helper.
                <br />
                Once the dog has completed a search to one side, the HF may call
                the dog to him with a short voice command and, while still
                moving (on the center line), he may send the dog in another
                direction with a new short voice command. The voice command used
                to recall the dog to the handler may be combined with the call
                name of the dog. The dog must always run in front of the handler
                (while crossing the field to search). When the dog reaches the
                last blind, the handler must stop and stand still, and no
                further commands are allowed.
                <br />
                2. Hold and bark (10 + 10 = 20 pts)
                <br />
                The dog must confront the Helper attentively and bark
                continuously. The dog may not jump on or grip the Helper. Upon a
                signal from the judge, the HF goes to his dog. Upon another
                signal from the judge, the HF will position himself, with his
                dog in the basic position, at a distance of one step (one pace)
                away from the Helper.
                <br />
                Now the HF orders the Helper to move out of the blind a distance
                of five steps (paces).
                <br />
                3. Attempted escape of the Helper (25 pts)
                <br />
                Upon a signal from the judge, the HF with his free-heeling dog
                steps away from the blind and positions himself at a distance of
                five paces from the Helper. The handler leaves his dog in a
                sifting position guarding the Helper, and goes back again into
                the blind (to search the blind for weapons etc.). Upon a signal
                from the judge, the Helper tries to escape. Upon a voice command
                from the HF, the dog must immediately and without hesitation
                prevent the escape by means of an energetic and strong grip.
                Upon a signal from the judge, the Helper stops and stands still.
                The dog must release upon a single command and must guard the
                Helper closely and attentively.
                <br />
                4. Defense of the dog during the guarding phase (25 pts)
                <br />
                After a guarding phase of about five seconds, and upon a signal
                from the judge, the Helper makes an attack upon the dog. Without
                any influence by the HF and without hesitation, the dog must
                defend itself through energetic and powerful griping. Upon a
                signal from the judge, the Helper stops and stands still. Upon a
                single short voice command, the dog must release and then guard
                the Helper closely and attentively. The HF goes to his dog and
                commands him to "heel". The stick is not taken from the Helper.
                <br />
                5. Attack on the dog out of motion (25 pts)
                <br />
                The handler with his dog will be sent into the middle of the
                field, at about the level of the third blind. The dog must sit
                next to his handler and may be hold by the collar.
                <br />
                Upon a signal from the judge, the Helper, carrying a stick,
                steps out from the sixth blind in a normal pace and walks
                directly toward the dog. As soon as the Helper has reached a
                point about 30 paces from the HF and his dog (which is sitting
                at heel beside him), the judge gives the HF a signal and the HF
                lets his dog go. The handler himself may not move from his
                position.
                <br />
                The Helper makes a frontal attack upon the dog, accompanied by
                intimidating utterances and fierce threatening motions. The dog
                must thwart the attack through energetic and powerful griping.
                Once the dog is griping the sleeve, he is to receive two stick
                hits. The hits are permitted on the thighs, side-portions
                (ribcage) and in the region of the withers (top of shoulder
                blades), Upon a signal from the judge, the Helper stops and
                stands still. Upon a single short voice command, the dog must
                release and then guard the Helper closely and attentively. Upon
                a signal from the judge, the HF goes to his dog, disarms the
                Helper, and positions himself for the start of the
                side-transport.
                <br />
                Now there is a side-transport of the Helper to the judge over a
                distance of about 20 paces. A short voice command at the start
                of the transport is allowed. The HF must walk on the right side
                of the Helper, with the dog between the HF and the Helper.
                During the transport, the dog may neither jump on nor grip the
                Helper. The group stops in front of the judge, and the HF
                presents the stick to the judge. The Helper leaves the field.
                The dog must remain in the free-heeling position while the dog
                and handier walk to the place where the critique will be given,
                and during the critique the dog must remain sitting free in the
                heel position. After the critique, the dog handler leaves the
                field with his dog heeling free beside him.
                <br />
              </Card.Text>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey='IPO2'>
          <Accordion.Button className='accordion-button bg-secondary'>
            IPO II
          </Accordion.Button>
          <Accordion.Body>
            <Card className='p-1 border-0' variant='light'>
              <Card.Text>
                <b>Part A: TRACKING</b>
                <br />
                Maximum score: 100 points Verbal command: "search/such" -staying
                on the track = 80 pts -two articles found = 20 pts
                <br />
                The track should be about 600 paces in length, laid by a
                stranger to the dog, with two right angle corners and at least
                30 minutes old.
                <br />
                The dog and handler should be out of sight when the stranger
                lays the trail. The judge will advise the track layer about the
                patters of the trial. The track layer will mark the start with a
                stake or marker and will remain on the right side of the stake
                for approximately one minute without trampling or marking the
                starting point. Two articles of approximately 15 centimeters
                (cm) in length, approximately 5-6 cm in width and approximately
                2-3 cm in thickness are to be handed to the tracklayer
                approximately 30 minutes prior to the laying of the track. Both
                articles are to placed on the track, the first article in the
                middle of the second leg and the second article at the end of
                the track. After placing the last article, the tracklayer will
                proceed about 10 paces straight ahead before deviating from the
                direction of the track. The articles are not to be laid near
                trees poles or other prominent markers. Articles of bright
                colors and articles that could be blown away during heavy wind
                conditions are not to be used.
                <br />
                Before the start, the handler will report to the judge with the
                dog on leash or equipped for tracking. The dog should sit at
                heel during the reporting process. The handler will advise the
                judge whether the dog will pick up or point out the articles. If
                a dog is trained to pick up the articles, it is permissible to
                stand, sit or return to the handler. It is not necessary for the
                pick up exercise to be performed the same both times. Both
                picking up and pointing out together is faulty. If a dog points
                out the articles, the excerise can be performed by laying down,
                sitting or standing and again it is not necessary to perform
                both indication in the same manner. It is faulty if the dog
                points out one article and picks up the other or drops the
                article during the pick up indication.
                <br />
                It is up to the discretion of the handler how the dog is to be
                started and whether to track on a leash or to track free without
                a leash. The tracking leash must be 10 meters in length and must
                be completely let out during the tracking phase' Should a
                handler choose to track without a tracking leash, the dog must
                be followed at a distance of 10-12 meters.
                <br />
                The track should be worked out by the dog in a quiet manner so
                that the handler can follow at a convenient pace. When the
                articles are found they are to be taken by the handler and shown
                to the judge by raising an arm. The articles are to be returned
                to the judge after the completion of the track.
                <br />
                Dispensing food for praising purpose is not permitted while on
                the track. After the completion of the track, the handler
                reports to the judge with the dog sifting at heel to present the
                articles. The judge may stop the evaluation if, after 15 minutes
                on the track, the end has not been successfully reached.
                <br />
                <b>Part B: OBEDIENCE</b>
                <br />
                Maximum score: 100 points
                <br />
                Each individual exercise begins and ends with the basic
                position.
                <br />
                The judge gives the signal to begin each exercise. All further
                movements, such as turns, halt, change of pace etc. will be
                carried out without signals from the judge; however, the handler
                is permitted to request the judge to give commands (signals) for
                all of these movements.
                <br />
                The transition from fast pace to slow pace must be shown without
                putting any normal-pace steps in between. The "left-about-turns"
                may be made in either of two ways.
                <br />
                1. Heeling on leash and impartiality (10 pts)
                <br />
                Verbal command.- "heel/Fuß"
                <br />
                The handler stands with his dog sifting beside him, on leash, in
                the basic position. When the handler gives the verbal command
                "heel/Full", the dog must go with him willingly. At the start of
                the exercise, the handler and dog must go straight out 40-50
                paces without stopping, make an about-turn, and after 10-15
                paces must show the fast pace and the slow pace (at least 10
                paces each.) Then, in normal pace, the handler and dog must
                perform at least one right turn, one left turn, and one
                about-turn. The dog must remain always on the left side of the
                handler, with his shoulder-blade level with the handler's knee;
                the dog may not forge, lag or travel out wide from the handler.
                <br />
                The "about-turn" is to be shown as a left-about-turn. The
                handler may give the verbal command "heel/Full" only when
                starting forward and when changing pace. When the handler stops,
                the dog must sit quickly without influence from the handler.
                During this procedure, the handler may not change his basic
                position and especially may not move to accommodate a
                crooked-sifting dog. The lead must be held in the handler's left
                hand throughout the exercise, and it must hang slack. Upon the
                judge's signal, the handler takes his dog through a group of at
                least four people. The handler must stop once in the group. The
                group members are to move about one another. Lagging, forging,
                heeling wide, and hesitation of the handler at the turns are
                faulty.
                <br />
                2. Free heeling (15 pts)
                <br />
                Verbal command: "heel/Fuß"
                <br />
                At the judge's instruction, the dog is unleashed while in the
                basic position. The handier hangs the lead around his shoulder
                or puts it in his pocket, and then proceeds immediately into the
                group with his free-heeling dog, stopping at least once in the
                group. After leaving the group, the handier briefly assumes the
                basic position, and then begins the free-heeling routine,
                following the same pattern as in the "heeling-on-leash"
                exercise. While the handler is performing the "free-heeling"
                exercise (but not while he is going through the Group), two
                shots (caliber 6-9 mm) are to be fired. The dog must remain
                indifferent to the gunshots.
                <br />
                Any dog showing gun-shyness will be immediately excused from the
                trial. It is definitely faulty if the dog shows a desire to
                attack at the sound of the shots, even though he remains under
                the handler's control. Only the dog that is indifferent to the
                gunshots will receive full points.
                <br />
                3. Sit out of motion (5 pts)
                <br />
                Verbal command: "sit/sitz"
                <br />
                From the basic position, the handler goes straight forward with
                his free-heeling dog. After at least ten paces, the handler
                gives the verbal command "sit/sitz", and the dog must sit
                quickly without the handler altering his pace or looking
                backwards. The handier goes on another 30 paces, then stops and
                turns to face his dog. At the judge's signal, the handier
                returns to the dog and takes up the basic position on the dog's
                right side. Up to 3 points will be deducted if the dog lies down
                or remains standing instead of sitting at the command.
                <br />
                4. Down with recall (10 pts)
                <br />
                Verbal commands: "down/platz"; "come/hier"; "heel/Fuß"
                <br />
                From the basic position, the handler gives the verbal command
                "heel/Fug" and proceeds straight forward with his dog. After at
                least ten paces, the verbal command "down/platz" is given and
                the dog must quickly lie down. Without any other influence on
                the dog and without turning around, the handler goes on a
                further 30 paces, turns immediately to face his dog, and remains
                standing still.
                <br />
                At the judge's signal, the handier recalls his dog. The dog must
                come in rapidly and happily and must sit close in front of the
                handler. On the verbal command "heel", the dog must quickly go
                to sit beside the handler. If the dog remains standing, or sits,
                but comes in perfectly, up to 5 points may be deducted.
                <br />
                5. Retrieving a I kg (2.2 lb) dumbbell on flat ground (10 pts)
                <br />
                Verbal command: "fetch/bring"
                <br />
                The dog sits free next to the handier. The handier throws a 1
                kilogram dumbbell about ten paces ( 30') away. Upon the single
                verbal command "fetch/bring", the dog must run rapidly to the
                thrown article, immediately pick it up, and return to the
                handler at a fast pace, The dog must sit close in front of the
                handler and must hold the article in his grasp until the
                handler, after a brief pause, takes the article from the dog
                with the verbal command "out/aus."
                <br />
                On the verbal command "heel/FW, the dog must rapidly go and sit
                on the left side of the handier. The handler must remain in the
                basic position throughout the exercise.
                <br />
                6. Retrieve 650 gram (1 (1/2) lb) dumbbell over brush-hurdle 1
                meter (39") high. 1.5 meters ( 5') wide (15 pts)
                <br />
                Verbal commands: "jump/hopp"; "fetch/bring"
                <br />
                The handler positions himself at an appropriate distance from
                the hurdle with his dog sitting free beside him. Then he throws
                the 650-gram wooden dumbbell over the hurdle. On the verbal
                commands "jump/hopp." "fetch/bring" the dog must perform a clean
                jump over the hurdle without disturbing it, immediately pick up
                the article, return over the hurdle, sit close in front of the
                handier, and hold the article in his grip until the handier,
                after a brief pause, takes the article from him with the verbal
                command "out/aus".
                <br />
                At the verbal command 'heel/FW, the dog must go rapidly to sit
                beside the handler The verbal command "fetch/bring" must be
                given before the dog has reached the dumbbell.
                <br />
                7. Scaling-jump over a slanted wall 1.9 m (6') high and 1.5 m
                (5') wide to retrieve handier's article (15 pts)
                <br />
                Verbal commands: "jump/hopp"; "fetch/bring"
                <br />
                The slanted wall consists of two boards, hinged at the top, each
                being a scaling board 1.5 m (5') wide and 1.9 m (6') tall. The
                lower parts of these boards, at ground level, are placed a
                certain distance apart from each other so as to produce a final
                vertical height of 1.6 m (5'2"). Each side of the wall has three
                wooden cleats fastened across it, each about 2448 mm (1"-2")
                wide. The handler positions himself an appropriate distance from
                the scaling wall while his dog sits free next to him. Instead of
                a handler's article, the handler may throw a dumbbell over the
                wall. At the verbal commands "jump," 'fetch" the dog must scale
                the wall, immediately pick up the article, return over the wall
                and sit close in front of the handler. He must keep the article
                in his grasp all the while, until the handier, after a brief
                pause, takes it from him with the verbal command "out/aus". At
                the verbal command "heel", the dog must rapidly go to sit beside
                the handler. The verbal command "fetch/bring" must be given
                before the dog has reached the dumbbell. 8. Send-away with down
                (10 pts) Verbal commands: "go out/voraus"; "down/platz";
                "sit/sitz" On the judge's signal, the handler with his
                free-heeling dog proceeds several steps straight ahead in the
                direction he has been instructed to go. Simultaneously the
                handler lifts his arm and gives the verbal command "go
                out/voraus" and stops still. The dog must go out at a fast pace
                at least 30 paces (75') in the indicated direction, and must lie
                down immediately upon the verbal command "down/platz." The
                handler may keep his arm raised high, to show the direction,
                until the dog has downed. On the judge's signal, the handler
                picks up his dog by returning to the right side of the dog and
                giving the verbal command 11 sit/sitz." Procedure Repeated
                raising and lowering of the arm is not allowed. The dog is to go
                out in a straight direction, but minor deviations are not
                faulty. Strong deviation, too short a go-out, too early or
                hesitant lying down, and standing up of the dog on the handier's
                return will result in point loss. 9. Down under distraction (I
                Opts) Verbal commands: "down/platz"; "sit/sitz" Before the start
                of another dog's obedience exercises, the handler downs his dog
                at a distance of about 40 paces, without leaving the leash or
                any type of article with the dog. Remaining within the dog's
                sight, the handler goes to a spot about 40 paces from the dog,
                without turning around, and there remains quietly standing with
                his back to the dog. The dog must remain lying there, without
                any influence from the handler, until the other dog working on
                the field has completed exercises 1-7. After exercise 7, the
                "downed" dog will be picked up by the same procedure as in
                exercise 8 above. Procedure: The handler must stand quietly with
                his back to his dog in that spot on the trialground where he was
                instructed to stand, until the judge instructs him to pick up
                his dog. Restless behavior of the handler, and other hidden
                help, as well as tooearly getting up of the dog when the handler
                returns, are faulty. If the dog stands or sits, but remains on
                the spot where he was put down, a partial score will be given .
                If, prior to the completion of exercise 4 by the dog on the
                field, the "downed" dog moves a distance of more than 3 meters (
                10') from where he was put down, the exercise will receive 0
                points. If the "downed" dog leaves his position after the other
                dog completes exercise 4, he will receive partial points. If the
                dog comes to meet the handier as the handler goes to pick him
                up, up to 3 points are deducted. Part C: PROTECTION WORK Maximum
                Score: 100 points 1. Searching for the Helper (5 pts) Six blinds
                are to be set up in a staggered fashion, three on each side,
                along the length of an area measuring about 100 meters (300 ft.)
                long and about 80 meters (240 ft.) wide. A Helper with a full
                protection suit, protection sleeve and soft stick is placed in
                the last (sixth) blind, out of sight of the dog. The handler
                (HF) positions himself, with his dog off leash sitting, beside
                him, on an imaginary midline at the level of the third blind. By
                raising one arm high into the air, the HF signals the judge that
                he is ready to begin the work. Upon a signal from the judge, the
                HF begins the Protection Work. Upon the handler giving short
                voice commands and hand signals with the right or left arm,
                which may be repeated, the dog must quickly leave the handler
                and run to and around four blinds in order. The HF must move
                along the imaginary midline, and may not leave this position
                during the search for the Helper. Once the dog has completed a
                search to one side, the HF may call the dog to him with a short
                voice command and, while still moving (on the center line), he
                may send the dog in another direction with a new short voice
                command. The voice command used to recall the dog to the handler
                may be combined with the callname of the dog. The dog must
                always run in front of the handier (while crossing the field to
                search), When the dog reaches the last blind, the handler must
                stop and stand still, and no further commands are allowed. 2.
                Hold and bark (5 + 5 = 10 pts) The dog must confront the Helper
                attentively and bark continuously. The dog may not jump on or
                grip the Helper. Upon a signal from the judge, the HF goes to
                his dog. Upon another signal from the judge, the HIP will
                position himself with his dog in the basic position, at a
                distance of one step (one pace) away from the Helper. Now the HF
                orders the Helper to move out of the blind a distance of five
                steps (paces). 3. Attempted escape of the Helper (20 pts) Upon a
                signal from the judge, the HF with his free-heeling dog steps
                away from the blind and positions himself at a distance of five
                paces from the Helper. The handler leaves his dog in a sitting
                position guarding the Helper, and goes back again into the blind
                (to search the blind for weapons etc.). Upon a signal from the
                judge, the Helper tries to escape. Upon a voice command from the
                HF, the dog must immediately and without hesitation prevent the
                escape by means of an energetic and strong grip. Upon a signal
                from the judge, the Helper stops and stands still. The dog must
                release upon a single command and must guard the Helper closely
                and attentively. 4. Defense of the dog during the guarding phase
                (20 pts) After a guarding phase of about five seconds, and upon
                a signal from the judge, the Helper makes an attack upon the
                dog. Without any influence by the HF and without hesitation, the
                dog must defend itself through energetic and powerful griping.
                Upon a signal from the judge, the Helper stops and stands still.
                Upon a single short voice command, the dog must release and then
                guard the Helper closely and attentively. The HF goes to his dog
                and prepares for the back-transport. The stick is not taken from
                the Helper, but the Helper must carry it so that the dog does
                not see it until Exercise 6. 5. Back-transport (5 pts) Next
                follows a back-transport of the Helper over a distance of about
                30 paces. The HF orders the Helper to go ahead, and walks with
                his dog at heel at a distance of 5 paces behind the Helper. A
                short voice command is permitted at the start of the
                back-transport. 6. Attack on the dog during the back-transport
                (20 pts) During the back-transport, and without stopping, the
                Helper will make an attack upon the dog. Without any influence
                by the HF and without hesitation, the dog must defend itself
                through energetic and powerful griping. Once the dog has seized
                the sleeve, the handler must stop and stand still. Upon a signal
                from the judge, the Helper stops and stands still. Upon a single
                short voice command, the dog must release and then guard the
                Helper closely and attentively. On the judge's signal, the HF
                goes to his dog, disarms the Helper, and positions himself for
                the side-transport. Next follows a side-transport of the Helper
                to the judge over a distance of about 20 paces. A short voice
                command at the start of the transport is allowed. The HF must
                walk on the right side of the Helper, with the dog between the
                HF and the Helper. During the transport, the dog may neither
                jump on nor grip the Helper. The group stops in front of the
                judge, and the HF presents the stick to the judge. The Helper
                leaves the field or goes into the sixth blind. 7. Attack on the
                dog out of motion (20 pts) The handler with his dog will be sent
                into the middle of the field, at about the level of the third
                blind. The dog, off leash, must sit next to his handler. Upon a
                signal from 'the judge, the Helper, carrying a stick, steps out
                from the sixth blind in a running pace and runs directly toward
                the dog. As soon as the Helper has reached a point about 30
                paces from the HF and his dog (which is sitting at heel beside
                him off leash), the judge gives the HF a signal and the HF
                (verbally) releases his dog. The handler himself may not move
                from his position. The Helper makes a frontal attack upon the
                dog, accompanied by intimidating utterances and fierce
                threatening motions. The dog must thwart the attack through
                energetic and powerful griping. Once the dog is griping the
                sleeve, he is to receive two stick hits. The hits are permitted
                on the thighs, side-portions (ribcage) and in the region of the
                withers (top of shoulder blades). Upon a signal from the judge,
                the Helper stops and stands still. Upon a single short voice
                command, the dog must release and then guard the Helper closely
                and attentively. Upon a signal from the judge, the HF goes to
                his dog, disarms the Helper, and positions himself for the start
                of the side-transport. Now there is a side-transport of the
                Helper to the judge over a distance of about 20 paces. A short
                voice command at the start of the transport is allowed. The HF
                must walk on the right side of the Helper, with the dog between
                the HF and the Helper. During the transport, the dog may neither
                jump on nor grip the Helper. The group stops in front of the
                judge, and the HF presents the stick to the judge. The Helper
                leaves the field. The dog must remain in the free-heeling
                position while the dog and handler walk to the place where the
                critique will be given , and during the critique the dog must
                remain sitting free in the heel position. After the critique,
                the dog handler leaves the field with his dog heeling free
                beside him.
              </Card.Text>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
        <Accordion.Item eventKey='IPO3'>
          <Accordion.Button className='accordion-button bg-secondary'>
            IPO III
          </Accordion.Button>
          <Accordion.Body>
            <Card className='p-1 border-0' variant='light'>
              <Card.Text>
                <b>Part A: TRACKING</b>
                <br />
                Maximum score: 100 points Verbal command: "search/such" -staying
                on the track = 80 pts -3 articles (7 + 7 + 6) = 20 pts
                <br />
                This is a search for lost articles on a stranger's track. The
                track is about 800 paces ( 2400') long, at least 60 minutes old,
                with three articles to be found. The dog may track free or on a
                10 meter ( 30') tracking line; both ways will be scored the
                same.
                <br />
                The stranger's track contains four right angles. The judge
                determines the lay of the track in conformity with the available
                tracking terrain. The tracks must be laid out differently; for
                instance, every track cannot have all the corners and all the
                articles at the same exact distances as the other tracks. The
                starting-point of the track must be well marked by a marker
                placed into the ground to the left of the starting-point. After
                the track layer has lingered a little while at the
                starting-point, he proceeds forward in the prescribed direction
                and places the first article after about 100 paces, and the
                second article in the middle of the second or third leg, without
                interrupting his stride. The third article is placed at the end
                of the track. After placing the last article, the track layer
                walks a few paces farther in the same direction, then steps
                sideways and returns out to one side of the track.
                <br />
                The handler may work his dog free or on the 10 meter ( 30')
                tracking line; both ways are scored equally. If the dog has not
                reached the end of the track within 20 minutes of his starting
                time, the judge will terminate the tracking work.
                <br />
                Procedure
                <br />
                The dog and handler must remain out of sight of the track during
                the laying of the track. The track-scent should, so far as
                possible, not be altered by the placing of the articles on the
                track. The track layer may not scuffle his feet or stop. The
                articles must be laid on the track, not beside it.
                <br />
                The handler prepares his dog for the tracking work. When called,
                he reports in to the judge with his dog and advises the judge
                whether the dog will pick up or indicate the articles. Doing
                both together (on the same track), i.e. picking up and
                indicating, is faulty.
                <br />
                On the judge's instruction, the dog is taken to the
                starting-point slowly and calmly and is started on the track.
                Any type of compulsion is to be avoided prior to the tracking,
                during the starting of the track, and throughout the tracking
                work. The dog is to be given ample time to take up the scent at
                the start of the track. Everything must be avoided that might
                tend to urge the dog to press forward impulsively.
                <br />
                The dog should take the scent calmly with a deep nose. As soon
                as the dog begins to track, the handler must stand still and let
                the 10-meter line slip freely through his hands. Then he follows
                his dog. If he is tracking without a line, the handler is to
                maintain the 30' distance behind his dog. As soon as the dog has
                found an article, he must immediately (without influence from
                the handler) pick up the article or indicate it convincingly. If
                the dog picks up articles he may then stand still, sit or return
                to the handler with the article. The dog may not lie down to
                pick up articles, nor may he proceed forward holding a picked up
                article; both are faulty behaviors. Indicating of articles may
                be done while lying down, sitting or standing still. When an
                article has been indicated, the handler drops the line and goes
                immediately to the dog. He shows that the dog has found the
                article by lifting the article high into the air. Then the
                handler continues the tracking work with his dog. After the end
                of the track, the handler must show the articles to the
                officiating judge.
                <br />
                It is permitted for the handler to go to the dog that has picked
                up an article. The tracking line may hang slack, so long as the
                handler does not let it fall from his hand (while tracking). The
                tracking line must be fastened to a breast-harness, Sniffing of
                the starting-stake is not faulty. If the handler gets to a point
                that is off the track by more than one length of the tracking
                line, the tracking is terminated.
                <br />
                If the dog leaves the track and the handler tries to hold him
                back, the judge will order the handler to follow the dog; if the
                handler holds the dog again, the tracking will be terminated.
                <br />
                <b>Part B: OBEDIENCE</b>
                <br />
                Maximum score: 100 points
                <br />
                Each individual exercise begins and ends with the basic
                position.
                <br />
                The judge gives the signal to begin each exercise. All further
                movements, such as turns, halt, change of pace etc. will be
                carried out without signals from the judge; however, the handler
                is permitted to request the judge to give commands (signals) for
                all of these movements. The transition from fast pace to slow
                pace must be shown without putting any normal-pace steps in
                between. The "leftabout-turns" may be made in either of two
                ways.
                <br />
                I. Free heeling (10 pts)
                <br />
                Verbal command: "heel/Fuß"
                <br />
                The handler reports in to the judge with his dog sitting
                off-leash beside him. The leash may not be visible to the dog.
                On the verbal command "heel/FuR", the dog must go with his
                handler willingly. At the start of the exercise, the handler and
                dog must go straight out 40-50 paces without stopping, make an
                about-turn, and after 10- 15 paces must show the fast pace and
                the slow pace (at least 10 paces each.) Then, in normal pace,
                the handler and dog must perform at least one right turn, one
                left turn, and one about-turn. The "about-turn" is to be shown
                as a leftabout-turn. The dog must always have his shoulder-blade
                at knee-level on the handler's left side, and the dog may not
                forge, lag or travel wide.
                <br />
                The handler may give the verbal command "heel/Fuß" only when
                starting forward and when changing pace. When the handler stops,
                the dog must sit quickly without influence from the handler.
                During this procedure, the handler may not change his basic
                position and especially may not move to accommodate a
                crooked-sitting dog.
                <br />
                Upon the judge's signal, the handler takes his dog through a
                group of at least four people. The handler must stop once in the
                group. The group members are to move about one another. Lagging,
                forging, heeling wide, and hesitation of the handler at the
                turns are faulty.
                <br />
                While the handler is performing the "free-heeling" exercise (but
                not while he is going through the Group), two shots (caliber 6-9
                mm) are to be fired. The dog must remain indifferent to the
                gunshots. Any dog showing gun-shyness will be immediately
                excused from the trial. It is definitely faulty if the dog shows
                a desire to attack at the sound of the shots, even though he
                remains under the handler's control. Only the dog that is
                indifferent to the gunshots will receive full points.
                <br />
                2. Sit out of motion (5 pts)
                <br />
                Verbal command: "sit/sitz"
                <br />
                From the basic position, the handler goes straight forward with
                his free-heeling dog. After at least ten paces, the handler
                gives the verbal command "sit/sitz", and the dog must sit
                quickly without the handler altering his pace or looking
                backwards. The handler goes on another 30 paces, then stops and
                turns to face his dog. At the judge's signal, the handler
                returns to the dog and takes up the basic position on the dog's
                right side. Up to 3 points will be deducted if the dog lies down
                or remains standing instead of sitting at the command.
                <br />
                3. Down with recall (10 pts)
                <br />
                Verbal commands: "down/platz"; "come/hier"; "heel/Fuß"
                <br />
                From the basic position, the handler proceeds straight forward
                with his dog for ten paces, then goes directly into a fast pace.
                After at least ten paces further, the verbal command
                "down/platz" is given and the dog must quickly lie down, without
                the handler altering his stride. Without looking back, the
                handler runs a further 30 paces, stops, and turns immediately to
                face his dog. After a short pause, the judge signals the handler
                to call the dog. The dog must come in rapidly and happily and
                sit close in front of the handler. On the verbal command "heel",
                the dog must go quickly to sit beside the handler. If the dog
                sits or remains standing after the verbal command to "down", up
                to 5 points may be deducted.
                <br />
                4. Stand out of walking pace (5 pts)
                <br />
                Verbal command: "stand/steh"'
                <br />
                From the basic position, the handler with his free-heeling dog
                goes straight ahead. After at least 10 paces, on the verbal
                command "stand/steh"', the dog" must quickly stop and stand
                still, without the handler altering his stride or looking
                backwards. After a further 30 paces, the handler stops and turns
                around to face the dog. On the judge's signal, the handler goes
                back to pick up the dog. The exercise ends after the handler has
                returned to the dog's right side, has given the verbal command
                "sit/sitz", and the dog is sitting.
                <br />
                5. Stand out of running pace (10 pts)
                <br />
                Verbal commands: "stand/steh"'; "come/hier"
                <br />
                From the basic position, the handler and his free-heeling dog
                run straight out. After at least 10 paces, the dog must stop
                still on the verbal command 11 stand/steh"' without the handler
                altering his stride or looking backward. After a further 30
                paces the handler stops and turns to face the dog. At the
                judge's signal the dog is recalled. The dog must come in fast
                and sit close in front of the handler. On the verbal command to
                "heel/FuR", the dog must quickly go to sit beside the handler.
                <br />
                6. Retrieving a 2 kg (4.4 lb) dumbbell on flat ground (10 pts)
                <br />
                Verbal command: "fetch/bring"
                <br />
                The dog sits free next to the handler. The handler throws a 2
                kilogram dumbbell about ten paces ( 30') away. Upon the single
                verbal command "fetch/bring", the dog must run rapidly to the
                dumbbell, immediately pick it up, and return to the handler at a
                fast pace. The dog must sit close in front of the handler and
                must hold the article in his grasp until the handler takes the
                it from him with the verbal command “out/aus.” On the verbal
                command "heel/Fuß", the dog must sit quickly beside the handler
                The handler must remain in the basic position until the dog has
                retrieved the dumbbell and is sitting next to him.
                <br />
                7. Retrievina a 650-gram ( 1 (1/2) lb dumbbell by a clean jump
                Over a brushhurdle I meter (39") high, 1.5 meters (5') wide (15
                pts))
                <br />
                Verbal commands.- "jump/hopp"; "fetch/bring"
                <br />
                The handler positions himself at an appropriate distance from
                the hurdle with his dog sifting free beside him. Then he throws
                the 650-gram wooden dumbbell over the hurdle. On the verbal
                commands "jump/hopp," "fetch/bring" the dog must perform a clean
                jump over the hurdle without disturbing it, immediately pick up
                the article , return over the hurdle, sit close in front of the
                handler, and hold the article in his grip until the handler,
                after a brief pause, takes the article from him with the verbal
                command "out/aus."
                <br />
                At the verbal command "heel/Fug", the dog must go rapidly to sit
                beside the handler. The verbal command "fetch/bring" must be
                given before ,the dog has reached the dumbbell.
                <br />
                8. Scaling-jump over a slanted wall 1.9 m (6') high and 1.6 m (
                5') wide to retrieve handler's article (15 pts)
                <br />
                Verbal commands: "jump/hopp"; "fetch/bring"
                <br />
                The slanted wall consists of two boards, hinged at the top, each
                being a scaling, board 1.5 m (5') wide and 1.9 m (6') tall. The
                lower parts of these boards, at ground level, are placed a
                certain distance apart from each other so as to produce a final
                vertical height of 1.8 m ( 6').
                <br />
                Each side of the wall has three wooden cleats fastened across
                it, each about 2448 mm (1"-2") wide.
                <br />
                The handler positions himself an appropriate distance from the
                scaling wall while his dog sits free next to him. Instead of a
                handler's article, the handler may throw a dumbbell over the
                wall. At the verbal commands “jump,” “fetch” the dog must scale
                the wall, immediately pick up the article, return over the wall
                and sit close in front of the handler. He must keep the article
                in his grasp all the while, until the handler, after a brief
                pause, takes it from him with the verbal command "out/aus". At
                the verbal command 'heel", the dog must rapidly go to sit beside
                the handler. The verbal command "fetch/bring" must be given
                before the dog has reached the dumbbell.
                <br />
                9. Send-away with down (10 pts)
                <br />
                Verbal commands: "go out/voraus"; "down/platz"; "sit/sitz"
                <br />
                On the judge's signal, the handler with his free-heeling dog
                proceeds several steps straight ahead in the direction he has
                been instructed to go. Simultaneously the handler lifts his arm
                and gives the verbal command "go out/voraus" and stops still.
                The dog must go out at a fast pace at least 40 paces (75') in
                the indicated direction, and must lie down immediately upon the
                verbal command "down/platz." The handler may keep his arm raised
                high, to show the direction, until the dog has downed. On the
                judge's signal, the handler picks up his dog by returning to the
                right side of the dog and giving the verbal command "sit/sitz".
                <br />
                10. Down under distraction (10 pts)
                <br />
                Verbal commands: "down/platz"; "sit/sitz"
                <br />
                Before the start of another dog's obedience exercises, the
                handler downs his dog at a distance of about 40 paces, without
                leaving the leash or any type of article with the dog. Now the
                handler, without looking around, proceeds at least 40 paces from
                his dog to a place out of the dog's sight but within the trial
                grounds. On the judge's signal, the handler picks up his dog by
                returning to the right side of the dog and giving the verbal
                command to "sit."
                <br />
                <b>Part C: PROTECTION WORK</b>
                <br />
                Maximum Score: 100 points
                <br />
                1. Searching for the Helper (10 pts)
                <br />
                Six blinds are to be set up in a staggered fashion, three on
                each side, along the length of an area measuring about 100
                meters (300 ft.) long and about 80 meters (240 ft.) wide. A
                Helper with a full protection suit, protection sleeve and soft
                stick is placed in the last (sixth) blind, out of sight of the
                dog.
                <br />
                The handler (HF) positions himself, with his dog off leash
                sitting beside him, on an imaginary midline at the level of the
                first blind. By raising one arm high into the air, the HF
                signals the judge that he is ready to begin the work. Upon a
                signal from the judge, the HF begins the Protection Work. Upon
                the handler giving short voice commands and hand signals with
                the right or left arm, which may be repeated, the dog must
                quickly leave the handler and run to and around the blinds in
                order. The HF must move along the imaginary midline, and may not
                leave this position during the search for the Helper.
                <br />
                Once the dog has completed a search to one side, the HF may call
                the dog to him with a short voice command and, while still
                moving (on the center line), he may send the dog in another
                direction with a new short voice command. The voice command used
                to recall the dog to the handier may be combined with the
                callname of the dog. The dog must always run in front of the
                handler (while crossing the field to search). When the dog
                reaches the last blind, the handier must stop and stand still,
                and no further commands are allowed.
                <br />
                2. Hold and bark (5 + 5 = 10 pts)
                <br />
                The dog must confront the Helper attentively and bark
                continuously. The dog may not jump on or e the Helper Upon a
                signal from the judge, the HF goes to his dog. Upon another
                signal from the judge, the HF will position himself with his dog
                in the basic position, at a distance of one step (one pace) away
                from the Helper. Now the HF orders the Helper to move out of the
                blind a distance of five steps (paces).
                <br />
                3. Attempted escape of the Helper (15 pts)
                <br />
                Upon a signal from the judge, the HF with his free-heeling dog
                steps away from the blind and positions himself at a distance of
                five paces from the Helper. The handler leaves his dog in a
                sifting position guarding the Helper, and goes back again into
                the blind (to search the blind for weapons etc.). Upon a signal
                from the judge, the Helper tries to escape. Upon a voice command
                from the HF, the dog must immediately and without hesitation
                prevent the escape by means of an energetic and strong grip.
                Upon a signal from the judge, the Helper stops and stands still.
                The dog must release upon a single command and must guard the
                Helper closely and attentively.
                <br />
                4. Defense of the dog during the guarding phase (15 pts)
                <br />
                After a guarding phase of about five seconds, and upon a signal
                from the judge, the Helper makes an attack upon the dog. Without
                any influence by the HF and without hesitation, the dog must
                defend itself through energetic and powerful griping. Once the
                dog is griping the sleeve, he is to receive two stick hits. The
                hits are permitted on the thighs, side-portions (rib-cage) and
                in the region of the withers (top of shoulder blades). Upon a
                signal from the judge, the Helper stops and stands still. Upon a
                single short voice command, the dog must release and then guard
                the Helper closely and attentively. The HF goes to his dog and
                prepares for the back-transport. The stick is not taken from the
                Helper, but the Helper must carry it so that the dog does not
                see it until Exercise 6.
                <br />
                5. Back-transport (5 pts)
                <br />
                Next follows a back-transport of the Helper over a distance of
                about 30 paces. The HF orders the Helper to go ahead, and walks
                with his dog at heel at a distance of 5 paces behind the Helper.
                A short voice command is permitted at the start of the
                back-transport.
                <br />
                6. Attack on the dog during the back transport (15 pts)
                <br />
                During the back-transport, and without stopping, the Helper will
                make an attack upon the dog. Without any influence by the HF and
                without hesitation, the dog must defend itself through energetic
                and powerful griping. Once the dog has seized the sleeve, the
                handier must stop and stand still.
                <br />
                Upon a signal from the judge, the Helper stops and stands still.
                Upon a single short voice command, the dog must release and then
                guard the Helper closely and attentively. On the judge's signal,
                the HF goes to his dog, disarms the Helper, and positions
                himself for the side-transport.
                <br />
                Next follows a side-transport of the Helper to the judge over a
                distance of about 20 paces. A short voice command at the start
                of the transport is allowed. The HF must walk on the right side
                of the Helper, with the dog between the HF and the helper.
                During the transport, the dog may neither jump on nor grip the
                Helper. The group stops in front of the judge, and the HF
                presents the stick to, the judge. The Helper leaves the field or
                goes into the sixth blind.
                <br />
                7. Attack on the dog out of motion (15 pts)
                <br />
                The handler with his dog will be sent into the middle of the
                field, at about the level of the third blind. The dog, off
                leash, must sit next to his handier.
                <br />
                Upon a signal from the judge, the Helper, carrying a stick,
                steps out from the sixth blind in a running pace and runs to the
                midline of the field. When he reaches the midline, the Helper
                turns toward the HF without breaking his stride (and begins
                running toward the HF). As soon as the Helper has reached a
                point about 30 paces from the HF and his dog (which is sitting
                at heel beside him off leash), the judge gives the HF a signal
                and the HF (verbally) releases his dog. The handier himself may
                not move from his position.
                <br />
                The Helper makes a frontal attack upon the dog, accompanied by
                intimidating utterances and fierce threatening motions. The dog
                must thwart the attack through energetic and powerful griping.
                Upon a signal from the judge, the Helper stops and stands still.
                Upon a single short voice command, the dog must release and then
                guard the Helper closely and attentively.
                <br />
                8. Defense of the dog during the guarding phase (15 pts)
                <br />
                After a guarding phase of about five seconds, and upon a signal
                from the judge, the Helper makes another attack upon the dog.
                Without any influence by the HF and without hesitation, the dog
                must defend itself through energetic and powerful griping. Once
                the dog is griping the sleeve, he is to receive two stick hits.
                The hits are permitted on the thighs, side-portions (rib-cage)
                and in the region of the withers (top of shoulder blades). Upon
                a signal from the judge, the Helper stops and stands still. Upon
                a single short voice command, the dog must release and then
                guard the Helper closely and attentively.
                <br />
                Upon the judge's signal, the HF goes to his dog, disarms the
                Helper, and prepares for the side-transport. Now there is a
                side-transport of the Helper to the judge over a distance of
                about 20 paces. A short voice command at the start of the
                transport is allowed. The HF must walk on the right side of the
                Helper, with the dog between the HF and the Helper. During the
                transport, the dog may neither jump on nor grip the Helper. The
                group stops in front of the judge, and the HF presents the stick
                to the judge. The Helper leaves the field. The dog must remain
                in the free-heeling position while the dog and handier walk to
                the place where the critique will be given, and during the
                critique the dog must remain sitting free in the heel position.
                After the critique, the dog handler leaves the field with his
                dog heeling free beside him.
              </Card.Text>
            </Card>
          </Accordion.Body>
        </Accordion.Item>
      </Accordion>
    </Container>
  );
};
