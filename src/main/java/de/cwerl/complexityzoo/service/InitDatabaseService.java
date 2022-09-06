package de.cwerl.complexityzoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.ComplexityDataType;
import de.cwerl.complexityzoo.model.data.normal.NormalComplexityClass;
import de.cwerl.complexityzoo.model.data.normal.Problem;
import de.cwerl.complexityzoo.model.data.para.ParaComplexityClass;
import de.cwerl.complexityzoo.model.data.para.Parameterization;
import de.cwerl.complexityzoo.model.relations.CTCRelation.CTCRelation;
import de.cwerl.complexityzoo.model.relations.CTCRelation.CTCRelationType;
import de.cwerl.complexityzoo.model.relations.CTPRelation.CTPRelation;
import de.cwerl.complexityzoo.model.relations.CTPRelation.CTPRelationType;
import de.cwerl.complexityzoo.model.relations.PTPRelation.PTPRelation;
import de.cwerl.complexityzoo.repository.data.ComplexityClassRepository;
import de.cwerl.complexityzoo.repository.data.ParameterizationRepository;
import de.cwerl.complexityzoo.repository.data.ProblemRepository;
import de.cwerl.complexityzoo.repository.relations.CTCRelationRepository;
import de.cwerl.complexityzoo.repository.relations.CTPRelationRepository;
import de.cwerl.complexityzoo.repository.relations.PTPRelationRepository;
import de.cwerl.complexityzoo.security.UserRepository;

@Service
public class InitDatabaseService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    ComplexityClassRepository classRepository;

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ParameterizationRepository parameterizationRepository;

    @Autowired
    CTCRelationRepository ctcRelationRepository;

    @Autowired
    CTPRelationRepository ctpRelationRepository;

    @Autowired
    PTPRelationRepository ptpRelationRepository;

    @Autowired
    PasswordEncoder encoder;

    public void init() {
        if(classRepository.count() == 0 && problemRepository.count() == 0 && parameterizationRepository.count() == 0) {
            ComplexityClass np = initComplexityClass("NP", ComplexityDataType.NORMAL);
            ComplexityClass twoSat = initComplexityClass("2-SAT", ComplexityDataType.NORMAL);
            ComplexityClass ac = initComplexityClass("AC", ComplexityDataType.NORMAL);
            ComplexityClass acZero = initComplexityClass("AC$^0$", ComplexityDataType.NORMAL);
            ComplexityClass acOne = initComplexityClass("AC$^1$", ComplexityDataType.NORMAL);
            ComplexityClass cfl = initComplexityClass("CFL", ComplexityDataType.NORMAL);
            ComplexityClass coNL = initComplexityClass("coNL", ComplexityDataType.NORMAL);
            ComplexityClass coNP = initComplexityClass("coNP", ComplexityDataType.NORMAL);
            ComplexityClass coRE = initComplexityClass("coRE", ComplexityDataType.NORMAL);
            ComplexityClass dcfl = initComplexityClass("DCFL", ComplexityDataType.NORMAL);
            ComplexityClass l = initComplexityClass("L", ComplexityDataType.NORMAL);
            ComplexityClass linspace = initComplexityClass("LINSPACE", ComplexityDataType.NORMAL);
            ComplexityClass nc = initComplexityClass("NC", ComplexityDataType.NORMAL);
            ComplexityClass ncOne = initComplexityClass("NC$^1$", ComplexityDataType.NORMAL);
            ComplexityClass nl = initComplexityClass("NL", ComplexityDataType.NORMAL);
            ComplexityClass p = initComplexityClass("P", ComplexityDataType.NORMAL);
            ComplexityClass pspace = initComplexityClass("PSPACE", ComplexityDataType.NORMAL);
            ComplexityClass r = initComplexityClass("R", ComplexityDataType.NORMAL);
            ComplexityClass re = initComplexityClass("RE", ComplexityDataType.NORMAL);
            ComplexityClass reg = initComplexityClass("REG", ComplexityDataType.NORMAL);
            ComplexityClass wOne = initComplexityClass("W[1]", ComplexityDataType.NORMAL);
            ComplexityClass fpt = initComplexityClass("FPT", ComplexityDataType.PARAMETERIZED);
            ComplexityClass paraACZero = initComplexityClass("para-AC$^0$", ComplexityDataType.PARAMETERIZED);
            ComplexityClass paraNP = initComplexityClass("para-NP", ComplexityDataType.PARAMETERIZED);
            ComplexityClass wP = initComplexityClass("W[P]", ComplexityDataType.PARAMETERIZED);
            ComplexityClass xacZero = initComplexityClass("XAC$^0$", ComplexityDataType.PARAMETERIZED);
            ComplexityClass wTwo = initComplexityClass("W[2]", ComplexityDataType.PARAMETERIZED);
            ComplexityClass xp = initComplexityClass("XP", ComplexityDataType.PARAMETERIZED);

            initCTCRelation(ac, nc, CTCRelationType.EQUAL_TO);
            initCTCRelation(ac, acZero, CTCRelationType.SUPERSET_OF);
            initCTCRelation(ac, acOne, CTCRelationType.SUPERSET_OF);
            initCTCRelation(ac, p, CTCRelationType.SUBSET_OF);
            initCTCRelation(acZero, acOne, CTCRelationType.SUBSET_OF);
            initCTCRelation(acZero, l, CTCRelationType.SUBSET_OF);
            initCTCRelation(acZero, nl, CTCRelationType.SUBSET_OF);
            initCTCRelation(acOne, ncOne, CTCRelationType.SUPERSET_OF);
            initCTCRelation(acOne, nl, CTCRelationType.SUPERSET_OF);
            initCTCRelation(cfl, dcfl, CTCRelationType.SUPERSET_OF);
            initCTCRelation(cfl, reg, CTCRelationType.PROPER_SUPERSET_OF);
            initCTCRelation(cfl, p, CTCRelationType.SUBSET_OF);
            initCTCRelation(coNL, nl, CTCRelationType.EQUAL_TO);
            initCTCRelation(coNL, l, CTCRelationType.SUPERSET_OF);
            initCTCRelation(coNP, p, CTCRelationType.SUPERSET_OF);
            initCTCRelation(coRE, re, CTCRelationType.NOT_EQUAL_TO);
            initCTCRelation(coRE, r, CTCRelationType.SUPERSET_OF);
            initCTCRelation(dcfl, reg, CTCRelationType.PROPER_SUPERSET_OF);
            initCTCRelation(fpt, wOne, CTCRelationType.SUBSET_OF);
            initCTCRelation(fpt, wP, CTCRelationType.SUBSET_OF);
            initCTCRelation(fpt, paraACZero, CTCRelationType.SUPERSET_OF);
            initCTCRelation(l, nl, CTCRelationType.SUBSET_OF);
            initCTCRelation(nc, ncOne, CTCRelationType.SUPERSET_OF);
            initCTCRelation(ncOne, reg, CTCRelationType.SUPERSET_OF);
            initCTCRelation(nl, p, CTCRelationType.SUBSET_OF);
            initCTCRelation(np, p, CTCRelationType.SUPERSET_OF);
            initCTCRelation(p, pspace, CTCRelationType.SUBSET_OF);
            initCTCRelation(paraACZero, xacZero, CTCRelationType.SUBSET_OF);
            initCTCRelation(paraACZero, paraNP, CTCRelationType.SUBSET_OF);
            initCTCRelation(paraNP, wP, CTCRelationType.SUPERSET_OF);
            initCTCRelation(r, re, CTCRelationType.SUBSET_OF);
            initCTCRelation(wOne, wTwo, CTCRelationType.SUBSET_OF);
            initCTCRelation(wOne, wP, CTCRelationType.SUBSET_OF);
            initCTCRelation(wTwo, wP, CTCRelationType.SUBSET_OF);
            initCTCRelation(wP, xp, CTCRelationType.SUBSET_OF);
            initCTCRelation(xacZero, xp, CTCRelationType.SUBSET_OF);

            Problem clique = initProblem("clique");
            Problem cnfSat = initProblem("CNF-SAT");
            Problem cvp = initProblem("CVP");
            Problem halt = initProblem("HALT");
            Problem independentSet = initProblem("Independent Set");
            Problem parity = initProblem("Parity");
            Problem sat = initProblem("SAT");
            Problem taut = initProblem("TAUT");
            Problem vertexCover = initProblem("Vertex Cover");

            Parameterization cliqueK = initParameterization("$k$", "The size of the soughted clique", clique);
            Parameterization independentSetK = initParameterization("$k$", "Size of the soughted independent set", independentSet);
            Parameterization independentSetTwG = initParameterization("$tw(G)$", "The tree width of the given graph", independentSet);
            Parameterization vertexCoverK = initParameterization("$k$","The size of the soughted vertex cover", vertexCover);

            initPTPRelation(clique, vertexCover, "$\\leq_P$");
            initPTPRelation(cliqueK, independentSetK, "$\\leq_{fpt}$");
            initPTPRelation(cnfSat, sat, "$\\leq_P$");
            initPTPRelation(independentSetK, cliqueK, "$\\leq_{fpt}$");
            initPTPRelation(sat, cnfSat, "$\\leq_P$");

            initCTPRelation(np, clique, CTPRelationType.IN);
            initCTPRelation(xacZero, cliqueK, CTPRelationType.IN);
            initCTPRelation(wTwo, cliqueK, CTPRelationType.IN);
            initCTPRelation(xp, cliqueK, CTPRelationType.IN);
            initCTPRelation(np, cnfSat, CTPRelationType.IN);
            initCTPRelation(p, cvp, CTPRelationType.IN);
            initCTPRelation(re, halt, CTPRelationType.IN);
            initCTPRelation(r, halt, CTPRelationType.NOT_IN);
            initCTPRelation(coRE, halt, CTPRelationType.NOT_IN);
            initCTPRelation(np, independentSet, CTPRelationType.IN);
            initCTPRelation(xacZero, independentSetK, CTPRelationType.IN);
            initCTPRelation(xp, independentSetK, CTPRelationType.IN);
            initCTPRelation(fpt, independentSetTwG, CTPRelationType.IN);
            initCTPRelation(acZero, parity, CTPRelationType.NOT_IN);
            initCTPRelation(ncOne, parity, CTPRelationType.IN);
            initCTPRelation(np, sat, CTPRelationType.IN);
            initCTPRelation(coNP, taut, CTPRelationType.IN);
            initCTPRelation(np, vertexCover, CTPRelationType.IN);
            initCTPRelation(paraACZero, vertexCoverK, CTPRelationType.IN);
            initCTPRelation(xp, vertexCoverK, CTPRelationType.IN);
        }
    }

    private ComplexityClass initComplexityClass(String name, ComplexityDataType type) {
        ComplexityClass c;
        if(type == ComplexityDataType.NORMAL) {
            c = new NormalComplexityClass(name);
        } else {
            c = new ParaComplexityClass(name);
        }
        classRepository.save(c);
        return c;
    }

    private Problem initProblem(String name) {
        Problem p = new Problem();
        p.setName(name);
        problemRepository.save(p);
        return p;
    }

    private Parameterization initParameterization(String name, String description, Problem parent) {
        Parameterization p = new Parameterization();
        p.setName(name);
        p.setParent(parent);
        parameterizationRepository.save(p);
        return p;
    }

    private CTCRelation initCTCRelation(ComplexityClass c1, ComplexityClass c2, CTCRelationType type) {
        CTCRelation ctc = new CTCRelation();
        ctc.setFirstClass(c1);
        ctc.setSecondClass(c2);
        ctc.setRelationType(type);
        ctcRelationRepository.save(ctc);
        return ctc;
    }

    private PTPRelation initPTPRelation(AbstractProblem p1, AbstractProblem p2, String type) {
        PTPRelation ptp = new PTPRelation();
        ptp.setFirstProblem(p1);
        ptp.setSecondProblem(p2);
        ptp.setRelationType(type);
        ptpRelationRepository.save(ptp);
        return ptp;
    }

    private CTPRelation initCTPRelation(ComplexityClass c, AbstractProblem p, CTPRelationType type) {
        CTPRelation ctp = new CTPRelation();
        ctp.setComplexityClass(c);
        ctp.setProblem(p);
        ctp.setRelationType(type);
        ctpRelationRepository.save(ctp);
        return ctp;
    }
}
